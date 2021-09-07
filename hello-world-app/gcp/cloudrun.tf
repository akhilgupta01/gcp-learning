resource "google_cloud_run_service" "crs-hello-world" {
  name = "crs-hello-world"
  location = var.region
  project = var.project

  template {
    spec {
      containers {
        image = "gcr.io/ag-learn-gcp/hello-world-app:1.8-SNAPSHOT"
      }
      service_account_name = "application-sa@${var.project}.iam.gserviceaccount.com"
    }
  }

  traffic {
    percent = 100
    latest_revision = true
  }
}

resource "google_cloud_run_service_iam_member" "member" {
  location = google_cloud_run_service.crs-hello-world.location
  project = google_cloud_run_service.crs-hello-world.project
  service = google_cloud_run_service.crs-hello-world.name
  role = "roles/run.invoker"
  member = "allUsers"
}