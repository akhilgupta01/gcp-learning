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
  default = "neat-encoder-329003"
}
variable "create_network"{
  type=bool
  description = "Toggle for Network creation"
  default = true
}
variable "create_vm_instances"{
  type=bool
  description = "Toggle for VM Instance creation"
  default = false
}
variable "create_cloud_build_triggers"{
  type=bool
  description = "Toggle for cloud build trigger creation"
  default = false
}
variable "enable_composer"{
  type=bool
  description = "Toggle for cloud composer enable/disable"
  default = true
}