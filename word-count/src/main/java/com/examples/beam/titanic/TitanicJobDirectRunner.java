package com.examples.beam.titanic;

import com.examples.beam.titanic.input.CsvInputParser;
import com.examples.beam.titanic.model.Passenger;
import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTagList;

import java.util.List;

import static com.examples.beam.titanic.Tags.INVALID;
import static com.examples.beam.titanic.Tags.VALID;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;

public class TitanicJobDirectRunner {

    public static void main(String[] args) {
        TitanicJobOptions options = PipelineOptionsFactory.as(TitanicJobOptions.class);
        options.setJobName("TitanicJob-a");
        options.setStagingLocation("target");
        options.setTempLocation("target/tmp");
        options.setRunner(DirectRunner.class);

        Pipeline pipeline = Pipeline.create(options);

        PCollectionTuple ingestedData = pipeline
                .apply("Read the input file", TextIO.read().from(options.getInput()))
                .apply("Parse passengers data", parsePassengersData());

        ingestedData.get(INVALID)
                .apply("Errors: Log parsing errors", TextIO.write().to(options.getInvalidOutput()).withNumShards(1));

        ingestedData.get(VALID)
                .apply("Convert to String", MapElements.into(strings()).via(String::valueOf))
                .apply("Write to file",  TextIO.write().to(options.getValidOutput()).withNumShards(1));


        pipeline.run().waitUntilFinish();
    }

    private static ParDo.MultiOutput<String, Passenger> parsePassengersData() {
        return ParDo.of(new CsvInputParser()).withOutputTags(VALID, TupleTagList.of(INVALID));
    }

    private static ParDo.SingleOutput<List<Passenger>, List<Passenger>> enrichSurvivalStatus() {
        return ParDo.of(new SurvivalStatusEnricher());
    }

}
