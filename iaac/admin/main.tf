terraform {
  required_version = "1.0.7"
  backend "gcs" {
    bucket="gcp-terraform-demo-329004-tfstate-admin"
  }
  required_providers {
    google = {
      source = "hashicorp/google"
    }
  }
}

provider "google" {
  version = "3.86.0"
  project = var.project
  region  = var.region
  zone    = var.zone
}