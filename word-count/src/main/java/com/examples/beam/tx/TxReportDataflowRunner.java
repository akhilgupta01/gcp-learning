package com.examples.beam.tx;

import com.examples.beam.core.model.EligibilityInfo;
import com.examples.beam.core.model.EligibilityWrapper;
import com.examples.beam.tx.functions.AggregateFunction;
import com.examples.beam.tx.functions.CheckEligibilityFn;
import com.examples.beam.tx.functions.EnrichFunction;
import com.examples.beam.tx.functions.TxStringParserFunction;
import com.examples.beam.tx.model.Transaction;
import com.examples.beam.tx.model.TxReport;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.metrics.*;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;

import java.util.Random;

import static org.apache.beam.sdk.values.TypeDescriptors.strings;

@Slf4j
public class TxReportDataflowRunner {

    public static void main(String[] args) {
        PipelineOptionsFactory.register(TxJobOptions.class);
        TxJobOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(TxJobOptions.class);
        Pipeline pipeline = Pipeline.create(options);

        PCollectionTuple ingestedData = pipeline
                .apply("Read input", TextIO.read().from(options.getTransactionFile()))
                .apply("Counter Metric", counterMetric("TxReport", "RawInput"))
                .apply("Parse CSV data", parseData());

        PCollection<EligibilityWrapper> eligibilityResult = ingestedData.get(TxReportTags.INGESTION_SUCCESS_TAG)
                .apply("Check Eligibility", ParDo.of(new CheckEligibilityFn()));

        eligibilityResult
                .apply("Extract Eligibility Status", ParDo.of(new DoFn<EligibilityWrapper, EligibilityInfo>() {
                    @ProcessElement
                    public void map(ProcessContext context) {
                        EligibilityWrapper eligibilityWrapper = context.element();
                        EligibilityInfo eligibilityInfo = EligibilityInfo.builder()
                                .businessId(eligibilityWrapper.getDataRecord().getBusinessId())
                                .eligible(eligibilityWrapper.isEligible())
                                .reason(eligibilityWrapper.getReason())
                                .build();
                        context.output(eligibilityInfo);
                    }
                }))
                .apply("to String", MapElements.into(strings()).via(EligibilityInfo::toString))
                .apply("Write to File", TextIO.write().to(options.getEligibilityResultFile()).withoutSharding());

        eligibilityResult
                .apply("Filter Eligible", Filter.by(EligibilityWrapper::isEligible))
                .apply("Counter Metric", counterMetric("TxReport", "Eligible"))
                .apply("UnWrap", MapElements.via(new SimpleFunction<EligibilityWrapper, Transaction>() {
                    public Transaction apply(EligibilityWrapper eligibleRecord) {
                        return (Transaction) eligibleRecord.getDataRecord();
                    }
                }))
                .apply("Create partitions", createPartitions(10))
                .apply("Create batches", GroupIntoBatches.ofSize(100L))
                .apply("Aggregate", ParDo.of(new EnrichFunction()))
                .apply("Flatten", Flatten.iterables())
                .apply("Map By ISIN", MapElements.via(new SimpleFunction<Transaction, KV<String, Transaction>>() {
                    public KV<String, Transaction> apply(Transaction transaction) {
                        return KV.of(transaction.getIsin(), transaction);
                    }
                }))
                .apply("Group By ISIN", GroupByKey.create())
                .apply("Aggregate", ParDo.of(new AggregateFunction()))
                .apply("to String", MapElements.into(strings()).via(TxReport::toString))
                .apply("Write Output", TextIO.write().to(options.getTransactionReport()).withoutSharding());

        try {
            PipelineResult pipelineResult = pipeline.run();
            pipelineResult.waitUntilFinish();

            MetricQueryResults metrics = pipelineResult.metrics().queryMetrics(
                    MetricsFilter.builder().addNameFilter(MetricNameFilter.inNamespace("TxReport")).build());

            for (MetricResult<Long> counter : metrics.getCounters()) {
                System.out.println(counter.getName() + ":" + counter.getAttempted());
            }

        } catch (UnsupportedOperationException e) {
            // do nothing
        }
    }

    private static <T> ParDo.SingleOutput<T, T> counterMetric(String namespace, String name) {
        return ParDo.of(new DoFn<T, T>() {
            private final Counter counter = Metrics.counter(namespace, name);

            @ProcessElement
            public void processElement(DoFn.ProcessContext context) {
                counter.inc();
                context.output(context.element());
            }
        });
    }

    private static <T> ParDo.SingleOutput<T, KV<Integer, T>> createPartitions(int numPartitions) {
        return ParDo.of(new DoFn<T, KV<Integer, T>>() {
            Random random = new Random();

            @ProcessElement
            public void assignRandomPartition(ProcessContext context) {
                context.output(KV.of(randomPartitionNumber(), context.element()));
            }

            private Integer randomPartitionNumber() {
                return random.nextInt(numPartitions);
            }
        });
    }

    private static ParDo.MultiOutput<String, Transaction> parseData() {
        return ParDo.of(new TxStringParserFunction())
                .withOutputTags(TxReportTags.INGESTION_SUCCESS_TAG, TxReportTags.INGESTION_FAILURE_TAGS);
    }
}
