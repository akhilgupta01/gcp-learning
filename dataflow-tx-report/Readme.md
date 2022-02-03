Dataflow Transaction Report
===

This module provides sample code and documentation about how to build and deploy a `Dataflow` pipeline to GCP environment.

### The Use Case

The module is used to implement a pipeline that can ingest raw transaction records and generate a report as an output.

___Input___
* A .csv file containing security transaction records

___Expected Output___
* A `Trade volume report` providing total traded volume per ISIN
* `Eligibility report` providing eligibility result along with reason for each transaction
* `Exception report` providing any details and category of exception for any transaction record (e.g. Missing Data, Corrupt Data, Failed Enrichment etc.)
* `Completeness report` providing count of records at each milestone

#### POC Objectives

Sl. No. | POC Objective | POC Finding |
--------| --------------|-------------|
1| How to create a small **batch** of records (limiting the max size) that can be passed to a web service to get PII data? | Using `GroupIntoBatches` option|
2| How to generate the **Audit Milestone** for the input records as it passes through various milestones | | 

Use following command to create a template
```
mvn compile exec:java -Dexec.mainClass=com.examples.beam.tx.TxReportDataflowRunner \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args="--runner=DataflowRunner \
--project=ag-trial-project1-a \
--stagingLocation=gs://ag-trial-project-a_work_dir/staging \
--tempLocation=gs://ag-trial-project-a_work_dir/working \
--templateLocation=gs://ag-trial-project-a_work_dir/templates/tx_report_job_template \
--region=us-central1"
```

Use following command to create a Job from a template
```
gcloud dataflow jobs run txreport-1 \
--gcs-location gs://ag-trial-project-a_work_dir/templates/tx_report_job_template \
--parameters transactionFile=gs://ag-trial-project-a_work_dir/incoming/transactions.csv \
--region us-central1 \
--staging-location gs://ag-trial-project-a_work_dir/working
```

#### Useful Links
- Creating Dataflow Templates
  - https://cloud.google.com/dataflow/docs/guides/templates/creating-templates
