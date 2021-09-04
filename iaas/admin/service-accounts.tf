#Service account to be used by cloud build trigger for deploying applications
resource "google_service_account" "app_deployer" {
  #name =
  account_id = "app-deployer"
  display_name = "app-deployer"
  description = "Service account used to deploy applications in this project"
}
resource "google_project_iam_member" "app_deployer_cloud_build_builder" {
  project = google_service_account.app_deployer.project
  role = "roles/cloudbuild.builds.builder"
  member = "serviceAccount:${google_service_account.app_deployer.email}"
}
resource "google_project_iam_member" "app_deployer_cloud_run_developer" {
  project = google_service_account.app_deployer.project
  role = "roles/run.developer"
  member = "serviceAccount:${google_service_account.app_deployer.email}"
}
resource "google_service_account_iam_member" "app_deployer_service_account_user" {
  service_account_id = google_service_account.application_sa.name
  role = "roles/iam.serviceAccountUser"
  member = "serviceAccount:${google_service_account.app_deployer.email}"
}

#Service Account to be used by application during runtime
resource "google_service_account" "application_sa" {
  #name =
  account_id = "application-sa"
  display_name = "application-sa"
  description = "Service account used by application during runtime"
}
resource "google_service_account_iam_member" "application_sa_service_account_user" {
  service_account_id = google_service_account.application_sa.name
  role = "roles/iam.serviceAccountUser"
  member = "serviceAccount:${google_service_account.application_sa.email}"
}

