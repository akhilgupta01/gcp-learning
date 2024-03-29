package com.examples.beam.tx;

import org.apache.beam.sdk.options.*;

public interface TxJobOptions extends PipelineOptions {
    @Description("Input transactions file")
    @Default.String("gs://ag-trial-project-a_work_dir/incoming/transactions.csv")
    @Validation.Required
    ValueProvider<String> getTransactionFile();
    void setTransactionFile(ValueProvider<String> value);

    @Description("Eligibility result of all transactions")
    @Default.String("gs://ag-trial-project-a_work_dir/outgoing/eligibility_result.csv")
    String getEligibilityResultFile();
    void setEligibilityResultFile(String eligibilityResultFile);

    @Description("Output Report")
    @Default.String("gs://ag-trial-project-a_work_dir/outgoing/transaction_report.csv")
    String getTransactionReport();
    void setTransactionReport(String transactionReport);
}
