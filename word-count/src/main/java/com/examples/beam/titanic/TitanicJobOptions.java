package com.examples.beam.titanic;

import org.apache.beam.sdk.extensions.gcp.options.GcpOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;

public interface TitanicJobOptions extends GcpOptions {
    @Description("Input for the pipeline")
    @Default.String("gs://ag-trial-project-1_work_dir/incoming/passengers.csv")
    String getInput();
    void setInput(String input);

    @Description("Valid Output for the pipeline")
    @Default.String("gs://ag-trial-project-1_work_dir/outgoing/titanic_valid.csv")
    String getValidOutput();
    void setValidOutput(String output);

    @Description("Invalid Output for the pipeline")
    @Default.String("gs://ag-trial-project-1_work_dir/outgoing/titanic_invalid.csv")
    String getInvalidOutput();
    void setInvalidOutput(String output);
}
