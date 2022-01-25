resource "google_compute_instance" "dev_instance" {
  name = "terraform-instance"
  machine_type = "n1-standard-1"
  allow_stopping_for_update = true
  labels = {
    "env":"dev"
  }
  metadata = {
    "gce-container-declaration" = "spec:\n  containers:\n    - name: instance-4\n      image: 'gcr.io/cloud-marketplace/google/nginx1:1.12'\n      stdin: false\n      tty: false\n  restartPolicy: Always\n"
    "google-logging-enabled": "true"
  }
  zone = var.zone
  tags = ["web","dev"]

  boot_disk {
    initialize_params {
      sourceImage: "projects/cos-cloud/global/images/family/cos-stable"
    }
  }
  network_interface {
    network = google_compute_network.vpc_network.name
  }
}