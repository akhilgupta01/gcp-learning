resource "google_service_account" "application_sa"{
  account_id = "application-sa"
  display_name = "Service Account used by application during runtime"
}