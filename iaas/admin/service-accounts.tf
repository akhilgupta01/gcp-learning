resource "google_service_account" "application_sa"{
  account_id = "application-sa"
  display_name = "Service account used by application during runtime"
}