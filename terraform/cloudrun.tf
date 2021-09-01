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

resource "google_cloud_run_service_iam_binding" "binding" {
  location = var.region
  project = var.project_id
  service = google_cloud_run_service.crs-hello-world.name
  role = "roles/run.admin"
  members = ["group:devops-sa@cloud-run-trials.iam.gserviceaccount.com"]
}
