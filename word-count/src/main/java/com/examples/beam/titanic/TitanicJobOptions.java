package com.examples.beam.titanic;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.ValueProvider;

public interface TitanicJobOptions extends PipelineOptions {
    @Description("Input for the pipeline")
    @Default.String("gs://ag-trial-project-a_work_dir/incoming/passengers1.csv")
    ValueProvider<String> getInput();
    void setInput(ValueProvider<String> input);

    @Description("Valid Output for the pipeline")
    @Default.String("gs://ag-trial-project-a_work_dir/outgoing/titanic_valid.csv")
    String getValidOutput();
    void setValidOutput(String output);

    @Description("Invalid Output for the pipeline")
    @Default.String("gs://ag-trial-project-a_work_dir/outgoing/titanic_invalid.csv")
    String getInvalidOutput();
    void setInvalidOutput(String output);
}
