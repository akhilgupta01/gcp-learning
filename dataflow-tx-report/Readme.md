Dataflow Transaction Report
===

This module provides sample code and documentation about how to build and deploy a `Dataflow` pipeline to GCP environment.

### The Use Case

The module is used to implement a pipeline that can ingest raw data and generate a report as an output.

___input___
* A .csv file containing security transaction records

## word-count in Dataflow
#### Useful Links
- Creating Dataflow Templates 
  - https://cloud.google.com/dataflow/docs/guides/templates/creating-templates


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
