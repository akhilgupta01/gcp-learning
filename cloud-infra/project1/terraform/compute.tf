#resource "google_compute_instance" "dev_instance" {
#  name = "terraform-instance3"
#  machine_type = "n1-standard-1"
#  allow_stopping_for_update = true
#  labels = {
#    "env":"dev"
#  }
#  metadata = {
#    "gce-container-declaration" = <<EOF
#    "spec:
#      containers:
#        - name: instance-4
#          image: 'gcr.io/cloud-marketplace/google/nginx1:1.12'
#          stdin: false
#          tty: false
#          restartPolicy: Always
#    "
#    EOF
#    "google-logging-enabled": "true"
#  }
#  zone = var.zone
#  tags = ["web","dev"]
#
#  boot_disk {
#    initialize_params {
#      image = "projects/cos-cloud/global/images/family/cos-stable"
#    }
#  }
#  network_interface {
#    network = google_compute_network.vpc_network.name
#  }
#}