terraform {
  required_version = "0.14.8"
  backend "gcs" {
    bucket="ag-gcp-learning-tfstate-adm"
  }
  required_providers {
    google = {
      source = "hashicorp/google"
    }
    google-beta = {
      source = "hashicorp/google-beta"
      version = "3.86.0"
    }
  }
}

provider "google" {
  version = "3.86.0"
  project = var.project
  region  = var.region
  zone    = var.zone
}

provider "google-beta" {
  version = "3.86.0"
  project = var.project
  region  = var.region
  zone    = var.zone
}





