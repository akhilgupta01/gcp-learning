resource "google_cloudfunctions_function" "startInstancePubSub" {
  name        = "startInstancePubSub"
  description = "Starts a VM Instance"
  runtime     = "nodejs14"

  event_trigger {
    event_type = "google.pubsub.topic.publish"
    resource   = "projects/${var.project}/topics/${google_pubsub_topic.start-instance-event.id}"
  }
  entry_point  = "startInstancePubSub"
  source_repository {
    url = "https://github.com/akhilgupta01/gcp-learning/blob/2932d30e3d908dab577486b6747fd54bd235d65e/iaac/admin/src/index.js"
  }
}