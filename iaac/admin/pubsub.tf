resource "google_pubsub_topic" "start-instance-event" {
  name = "start-instance-event"
  message_retention_duration = "86600s"
}