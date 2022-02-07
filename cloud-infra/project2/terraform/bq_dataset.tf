resource "google_bigquery_dataset" "dataset" {
  dataset_id    = "common_reference_dataset"
  friendly_name = "crd"
  description   = "This contains various reference data"
}

resource "google_bigquery_table" "country" {
  dataset_id = google_bigquery_dataset.dataset.dataset_id
  table_id   = "country"
  deletion_protection=false
  schema = <<EOF
  [
    {"name": "id", "type": "STRING", "mode": "Required","description": "Country ID"},
    {"name": "name", "type": "STRING", "mode": "Required","description": "Name of the country"}
  ]
  EOF
}

resource "google_bigquery_table" "gates" {
  dataset_id = google_bigquery_dataset.dataset.dataset_id
  table_id   = "gates"
  deletion_protection=false
  schema = <<EOF
  [
    {"name": "code", "type": "STRING", "mode": "Required","description": "Gate Code"},
    {"name": "name", "type": "STRING", "mode": "Required","description": "Gate Name"}
  ]
  EOF
}
