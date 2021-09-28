resource "google_composer_environment" "composer_env"{
  count = var.enable_composer ? 1:0
  name = "composer-env"
  region = var.region
  project = var.project
  config {
    node_count = 3
    node_config {
      zone = var.zone
      machine_type = "n1-standard-1"
      network = google_compute_network.vpc_network.id
      subnetwork = google_compute_subnetwork.subnetwork.id
      service_account = google_service_account.composer_sa.name
      ip_allocation_policy {
        use_ip_aliases = true
      }
    }
    private_environment_config {
      enable_private_endpoint = true
      cloud_sql_ipv4_cidr_block = "10.0.0.0/12"
      master_ipv4_cidr_block = "172.16.10.0/23"
      web_server_ipv4_cidr_block = "172.31.250.0/24"
    }
    software_config {
      python_version = "3"
      image_version = "composer-1.16.6-airflow-1.10.15"
      pypi_packages = {
        google-api-python-client = ""
      }
      env_variables = {
        FOO = "bar"
      }
    }
  }
}