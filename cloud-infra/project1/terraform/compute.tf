resource "google_compute_instance" "dev_instance" {
  name = "terraform-instance"
  machine_type = "f1-micro"
  #machine_type = "n2-standard-2"
  allow_stopping_for_update = true
  labels = {"env":"dev"}
  zone = var.zone
  tags = ["web","dev"]

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }
  metadata {
    gce-container-declaration = "spec:\n  containers:\n    - name: instance-4\n      image: 'gcr.io/cloud-marketplace/google/nginx1:1.12'\n      stdin: false\n      tty: false\n  restartPolicy: Always\n"
  }
  network_interface {
    network = google_compute_network.vpc_network.name
  }
}