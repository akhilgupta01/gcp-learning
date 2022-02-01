package com.examples.beam.tx;

import com.examples.beam.core.model.EligibilityInfo;
import com.examples.beam.core.model.EligibilityWrapper;
import com.examples.beam.tx.functions.AggregateFunction;
import com.examples.beam.tx.functions.CheckEligibilityFn;
import com.examples.beam.tx.functions.TxStringParserFunction;
import com.examples.beam.tx.model.Transaction;
import com.examples.beam.tx.model.TxReport;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;

import static org.apache.beam.sdk.values.TypeDescriptors.strings;

@Slf4j
public class TxReportDataflowRunner {

    public static void main(String[] args) {
        PipelineOptionsFactory.register(TxJobOptions.class);
        TxJobOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(TxJobOptions.class);
        Pipeline pipeline = Pipeline.create(options);

        PCollectionTuple ingestedData = pipeline
                .apply("Read input", TextIO.read().from(options.getTransactionFile()))
                .apply("Parse CSV data", parseData());

        PCollection<EligibilityWrapper> eligibilityResult = ingestedData.get(TxReportTags.INGESTION_SUCCESS_TAG)
                .apply("Check Eligibility", ParDo.of(new CheckEligibilityFn()));

        eligibilityResult
                .apply("Extract Eligibility Status", ParDo.of(new DoFn<EligibilityWrapper, EligibilityInfo>() {
                    @ProcessElement
                    public void map(ProcessContext context){
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
                .apply("Map By ISIN", MapElements.via(new SimpleFunction<EligibilityWrapper, KV<String, Transaction>>() {
                    public KV<String, Transaction> apply(EligibilityWrapper eligibleRecord) {
                        Transaction transaction = (Transaction)eligibleRecord.getDataRecord();
                        return KV.of(transaction.getIsin(), transaction);
                    }
                }))
                .apply("Group By ISIN", GroupByKey.create())
                .apply("Aggregate" , ParDo.of(new AggregateFunction()))
                .apply("to String", MapElements.into(strings()).via(TxReport::toString))
                .apply("Write Output", TextIO.write().to(options.getTransactionReport()).withoutSharding());

        try {
            pipeline.run().waitUntilFinish();
        }catch(UnsupportedOperationException e) {
            // do nothing
        }
    }

    private static ParDo.MultiOutput<String, Transaction> parseData() {
        return ParDo.of(new TxStringParserFunction()).withOutputTags(TxReportTags.INGESTION_SUCCESS_TAG, TxReportTags.INGESTION_FAILURE_TAGS);
    }
}
