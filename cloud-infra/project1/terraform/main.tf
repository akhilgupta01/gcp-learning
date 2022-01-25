terraform {
  required_version = "1.0.7"
  backend "gcs" {
    bucket="gcp-learning-tfstate-admin"
  }
  required_providers {
    google = {
      source = "hashicorp/google"
    }
  }
}

provider "google" {
  version = "4.8.0"
  project = var.project
  region  = var.region
  zone    = var.zone
}