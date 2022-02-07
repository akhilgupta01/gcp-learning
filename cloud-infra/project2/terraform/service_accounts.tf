resource "google_project_iam_member" "application_sa_bq_job_user" {
  project = var.project
  role = "roles/bigquery.jobUser"
  member = "serviceAccount:application-sa@ag-trial-project-a.iam.gserviceaccount.com"
}
resource "google_project_iam_member" "application_sa_bq_data_viewer" {
  project = var.project
  role = "roles/bigquery.dataViewer"
  member = "serviceAccount:application-sa@ag-trial-project-a.iam.gserviceaccount.com"
}