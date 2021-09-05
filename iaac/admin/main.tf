terraform {
  required_version = "0.14.8"
  backend "gcs" {
    bucket="ag-gcp-learning-tfstate-adm"
  }
  required_providers {
    google = {
      source = "hashicorp/google"
    }
  }
}

provider "google" {
  version = "3.82.0"
  project = var.project
  region  = var.region
  zone    = var.zone
}





