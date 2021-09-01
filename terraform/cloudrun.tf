resource "google_cloud_run_service" "crs-hello-world" {
  name = "crs-hello-world"
  location = var.region
  project = var.project_id

  template {
    spec {
      containers {
        image = "gcr.io/cloudrun/hello"
      }
      service_account_name = "${google_service_account.application_sa.account_id}@cloud-run-trials.iam.gserviceaccount.com"
    }
  }

  traffic {
    percent = 100
    latest_revision = true
  }
}