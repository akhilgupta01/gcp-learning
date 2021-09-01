resource "google_project_iam_member" "devops-sa-role-iam-sa-user" {
  project = var.project_id
  role = "roles/iam.serviceAccountUser"
  member = "serviceAccount:devops-sa@cloud-run-trials.iam.gserviceaccount.com"
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

resource "google_cloud_run_service_iam_binding" "binding" {
  location = var.region
  project = var.project_id
  service = google_cloud_run_service.crs-hello-world.name
  role = "roles/iam.serviceAccountUser"
  members = ["group:devops-sa@cloud-run-trials.iam.gserviceaccount.com"]
}
