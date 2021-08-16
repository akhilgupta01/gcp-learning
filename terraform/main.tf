terraform {
  required_providers {
    google = {
      source = "hashicorp/google"
    }
  }
}

provider "google" {
  version = "3.5.0"
  project = "tf-trials-323004"
  region  = "us-central1"
  zone    = "us-central1-a"
}




