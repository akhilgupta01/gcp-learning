resource "google_cloud_run_service_iam_binding" "binding" {
  location = google_cloud_run_service.crs-hello-world.location
  project = google_cloud_run_service.crs-hello-world.project
  service = google_cloud_run_service.crs-hello-world.name
  role = "roles/run.admin"
  members = ["group:devops-sa@cloud-run-trials.iam.gserviceaccount.com"]
}

resource "google_cloud_run_service" "crs-hello-world" {
  name = "crs-hello-world"
  location = var.region
  project = var.project_id

  template {
    spec {
      containers {
        image = "gcr.io/cloudrun/hello"
      }
    }
  }

  traffic {
    percent = 100
    latest_revision = true
  }
}

