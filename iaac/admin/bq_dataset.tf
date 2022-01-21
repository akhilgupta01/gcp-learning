resource "google_bigquery_dataset" "dataset" {
  dataset_id                  = "transactions_dataset"
  friendly_name               = "transactions"
  description                 = "This is a test dataset for transactions"
}