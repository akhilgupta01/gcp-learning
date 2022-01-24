resource "google_cloudfunctions_function" "startInstancePubSub" {
  name        = "startInstancePubSub"
  description = "Starts a VM Instance"
  runtime     = "nodejs14"

  event_trigger {
    event_type = "google.pubsub.topic.publish"
    resource   = google_pubsub_topic.start-instance-event.id
  }
  entry_point  = "startInstancePubSub"
  source_archive_bucket = "ag-trial-project-1_work_dir/sources"
  source_archive_object = "index.zip"
}