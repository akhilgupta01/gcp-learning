resource "google_bigquery_dataset" "dataset" {
  dataset_id                  = "transactions_dataset"
  friendly_name               = "transactions"
  description                 = "This is a test dataset for transactions"
}

resource "google_bigquery_table" "default" {
  dataset_id = google_bigquery_dataset.dataset.dataset_id
  table_id   = "system-x"

  time_partitioning {
    type = "DAY"
  }

  labels = {
    env = "default"
  }

  schema = <<EOF
[
  {"name": "tx_id", "type": "STRING", "mode": "Required","description": "ID of the transaction"},
  {"name": "tx_date", "type": "DATETIME", "mode": "Required","description": "Datetime when the transaction was created"}
]
EOF

}