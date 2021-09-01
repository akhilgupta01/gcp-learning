resource "google_service_account" "application_sa" {
  account_id = "application-sa"
  display_name = "Service account used by application during runtime"
}

resource "google_service_account_iam_binding" "application_sa_user" {
  service_account_id = google_service_account.application_sa.account_id
  role = "roles/iam.serviceAccountUser"
}