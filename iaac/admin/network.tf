resource "google_compute_network" "vpc_network" {
  count = var.create_network ? 1:0
  name = "learn-gcp-network"
  auto_create_subnetworks = false
}

resource "google_compute_subnetwork" "subnetwork"{
  project = var.project
  name = "${google_compute_network.vpc_network[0].name}-${var.region}"
  network = google_compute_network.vpc_network[0].name
  region = var.region
  ip_cidr_range = "10.154.0.0/20"
  private_ip_google_access = true
  log_config {
    aggregation_interval = "INTERVAL_10_MIN"
    flow_sampling = 0.5
    metadata = "INCLUDE_ALL_METADATA"
  }

}
//resource "google_compute_address" "vm_static_ip" {
//  name = "terraform-static-ip"
//}