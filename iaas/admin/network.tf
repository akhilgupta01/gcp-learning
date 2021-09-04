resource "google_compute_network" "vpc_network" {
  count = var.create_network ? 1:0
  name = "terraform-network"
}

//resource "google_compute_address" "vm_static_ip" {
//  name = "terraform-static-ip"
//}