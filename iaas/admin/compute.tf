resource "google_compute_instance" "vm_instance" {
  count = var.create_vm_instances ? 1 : 0
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
    network = google_compute_network.vpc_network[0].name
    //    access_config {
    //      nat_ip = google_compute_address.vm_static_ip.address
    //    }
  }
}