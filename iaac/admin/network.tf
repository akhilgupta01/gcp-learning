resource "google_compute_network" "vpc_network" {
  name = "learn-gcp-network"
  auto_create_subnetworks = false
}
