#app deployer service account
resource "google_service_account" "app_deployer" {
  #name =
  account_id = "app-deployer"
  description = "Service account used to deploy applications in this project"
}
resource "google_service_account_iam_member" "app_deployer" {
  service_account_id = google_service_account.app_deployer.name
  role = "roles/iam.serviceAccountUser"
  member = "serviceAccount:${google_service_account.application_sa.email}"
}


resource "google_service_account" "application_sa" {
  #name =
  account_id = "application-sa"
  description = "Service account used by application during runtime"
}

resource "google_service_account_iam_member" "application_sa_user" {
  service_account_id = google_service_account.application_sa.name
  role = "roles/iam.serviceAccountUser"
  member = "serviceAccount:${google_service_account.application_sa.email}"
}