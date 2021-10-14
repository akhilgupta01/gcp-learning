variable "region"{
  type=string
  description = "Project Region"
  default = "us-central1"
}

variable "zone"{
  type=string
  description = "Project Zone"
  default = "us-central1-a"
}

variable "project"{
  type=string
  description = "Project Id"
  default = "gcp-terraform-demo-329004"
}
