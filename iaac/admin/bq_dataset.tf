resource "google_bigquery_dataset" "dataset" {
  dataset_id    = "transactions_dataset"
  friendly_name = "transactions"
  description   = "This is a test dataset for transactions"
}

resource "google_bigquery_table" "default" {
  dataset_id = google_bigquery_dataset.dataset.dataset_id
  table_id   = "passengers"
  deletion_protection=false

  external_data_configuration {
    autodetect    = true
    source_format = "CSV"
    source_uris = [
      "gs://ag-trial-project-1_work_dir/incoming/passengers.csv",
    ]
  }
}