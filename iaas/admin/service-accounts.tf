resource "google_service_account" "application_sa" {
  name = "application-sa"
  account_id = "application-sa2"
  description = "Service account used by application during runtime"
}

resource "google_service_account_iam_member" "application_sa_user" {
  service_account_id = google_service_account.application_sa.name
  role = "roles/iam.serviceAccountUser"
  member = "serviceAccount:${google_service_account.application_sa.email}"
}