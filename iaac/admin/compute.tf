resource "google_compute_instance" "vm_instance" {
  name = "terraform-instance"
  machine_type = "f1-micro"
  zone = var.zone
  tags = [
    "web",
    "dev"]

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }
  network_interface {
    network = google_compute_network.vpc_network.name
  }
}