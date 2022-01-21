resource "google_storage_bucket" "work_dir" {
  name          = "work_dir"
  uniform_bucket_level_access = true
}