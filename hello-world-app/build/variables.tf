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
  default = "ag-learn-gcp"
}

variable "create_vm_instances"{
  type=bool
  description = "Toggle for VM Instance creation"
  default = false
}