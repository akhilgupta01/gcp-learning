terraform {
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
  version = "3.5.0"
  project = var.project
  region  = var.region
  zone    = var.zone
}





