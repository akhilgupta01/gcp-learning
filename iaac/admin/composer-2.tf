resource "google-beta_composer_environment" "composer_env2" {
  provider = google-beta
  count    = var.enable_composer ? 1 : 0
  name     = "composer2-env"
  region   = var.region
  project  = var.project
  config {
    node_config {
      zone = var.zone
      network         = google_compute_network.vpc_network.id
      subnetwork      = google_compute_subnetwork.subnetwork.id
      service_account = google_service_account.composer_sa.name
    }
    private_environment_config {
      enable_private_endpoint   = true
      cloud_sql_ipv4_cidr_block = "10.0.0.0/12"
      master_ipv4_cidr_block    = "172.16.10.0/23"
    }
    software_config {
      image_version = "composer-2.0.0-preview.2-airflow-2.1.2"
      pypi_packages = {
        google-api-python-client = ""
      }
      env_variables = {
        FOO = "bar"
      }
    }
  }
}