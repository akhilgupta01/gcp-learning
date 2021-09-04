resource "google_cloudbuild_trigger" "hello-world-app-deploy" {
  name = "hello-world-app-deploy"
  description = "Deploys Hello World application"
  github {
    owner = "akhilgupta01"
    name = "gcp-learning"
    push{
      branch = "master"
    }
  }

  filename = "iaas/dev/cloudbuild.yaml"
  included_files = ["iaas/dev/**"]
//  service_account = google_service_account.app_deployer.email #Not Supported
}