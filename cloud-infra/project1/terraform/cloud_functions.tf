# Generates an archive of the source code compressed as a .zip file.
data "archive_file" "source" {
  type        = "zip"
  source_dir  = "../src"
  output_path = "/tmp/startInstancePubSub.zip"
}

# Add source code zip to the Cloud Function's bucket
resource "google_storage_bucket_object" "cloud_functions" {
  source       = data.archive_file.source.output_path
  content_type = "application/zip"

  # Append to the MD5 checksum of the files's content
  # to force the zip to be updated as soon as a change occurs
  name   = "sources/src-${data.archive_file.source.output_md5}.zip"
  bucket = google_storage_bucket.work_dir.name

  depends_on = [data.archive_file.source]
}


resource "google_cloudfunctions_function" "manage_vm_instance" {
  name        = "manage_vm_instance"
  description = "Starts or Stops a VM Instance"
  runtime     = "python39"

  event_trigger {
    event_type = "google.pubsub.topic.publish"
    resource   = google_pubsub_topic.vm-instance-manage-event.id
  }
  entry_point           = "manage_vm_instance"
  source_archive_bucket = google_storage_bucket.work_dir.name
  source_archive_object = "sources/src-${data.archive_file.source.output_md5}.zip"
  service_account_email = google_service_account.vm_manager_sa.email
}
