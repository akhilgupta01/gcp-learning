resource "google_cloud_run_service" "crs-hello-world" {
  name = "crs-hello-world"
  location = var.region

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