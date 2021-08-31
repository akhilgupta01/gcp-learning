terraform {
  backend "gcs" {
    bucket="cloud-run-trials-tfstate"
  }
  required_providers {
    google = {
      source = "hashicorp/google"
    }
  }
}

provider "google" {
  version = "3.5.0"
  project = var.project_id
  region  = var.region
  zone    = var.zone
}





