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
resource "google_project_iam_member" "app_deployer_security_admin" {
  project = google_service_account.app_deployer.project
  role = "roles/iam.securityAdmin"
  member = "serviceAccount:${google_service_account.app_deployer.email}"
}


#Service Account to be used by application during runtime
resource "google_service_account" "application_sa" {
  account_id = "application-sa"
  display_name = "application-sa"
  description = "Service account used by application during runtime"
}
resource "google_service_account_iam_member" "app_deployer_service_account_user" {
  service_account_id = google_service_account.application_sa.name
  role = "roles/iam.serviceAccountUser"
  member = "serviceAccount:${google_service_account.app_deployer.email}"
}
resource "google_project_iam_member" "application_sa_storage_object_creator" {
  project = google_service_account.app_deployer.project
  role = "roles/storage.objectCreator"
  member = "serviceAccount:${google_service_account.application_sa.email}"
}
resource "google_project_iam_member" "application_sa_storage_object_viewer" {
  project = google_service_account.app_deployer.project
  role = "roles/storage.objectViewer"
  member = "serviceAccount:${google_service_account.application_sa.email}"
}
resource "google_project_iam_member" "application_sa_bq_job_user" {
  project = google_service_account.app_deployer.project
  role = "roles/bigquery.jobUser"
  member = "serviceAccount:${google_service_account.application_sa.email}"
}


#Service account to be used by cloud composer
#resource "google_service_account" "composer_sa" {
#  account_id = "composer-sa"
#  display_name = "composer-sa"
#  description = "Service account used by Cloud Composer"
#}
#resource "google_project_iam_member" "composer-worker" {
#  role   = "roles/composer.worker"
#  member = "serviceAccount:${google_service_account.composer_sa.email}"
#}
#resource "google_service_account_iam_member" "composer_sa_access_to_devops_admin" {
#  service_account_id = google_service_account.composer_sa.name
#  role = "roles/iam.serviceAccountUser"
#  member = "serviceAccount:devops-admin@ag-learn-gcp.iam.gserviceaccount.com"
#}
