export PROJECT_ID=tf-trials-323004
export REGION=us-central1
export ZONE=us-central1-a

gcloud config set project ${PROJECT_ID}
gcloud config set compute/zone ${ZONE}
gcloud config set compute/region ${REGION}

#Stop a VM
gcloud compute instances stop terraform-instance

#Start a VM
gcloud compute instances start terraform-instance
