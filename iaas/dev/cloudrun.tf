resource "google_cloud_run_service" "crs-hello-world" {
  name = "crs-hello-world"
  location = var.region
  project = var.project

  template {
    spec {
      containers {
        image = "gcr.io/cloudrun/hello"
      }
      service_account_name = "application-sa@${var.project}.iam.gserviceaccount.com"
    }
  }

  traffic {
    percent = 100
    latest_revision = true
  }
}