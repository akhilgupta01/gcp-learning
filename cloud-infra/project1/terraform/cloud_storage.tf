resource "google_storage_bucket" "work_dir" {
  name          = "${var.project}_work_dir"
  uniform_bucket_level_access = true
}