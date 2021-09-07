GCP Learning
===
---
In this module we will learn the following:
1. Infrastructure as a Code in GCP using Terraform
---

### Create a new Project in GCP

Let us assume that we created a project with name `ag-gcp-learning`

### Prepare for Terraform

Terraform requires to know the current state of the infrastructure. It captures the state in a file. We need to provide
a location where it can store the state. We will keep this state on the Google Cloud Storage. For this we have to do the
following:

* Enable the `Cloud Storage API`
* Create a new Cloud Storage bucket, lets call it as `ag-gcp-learning-tfstate-adm`
* Refer to this bucket as the backend bucket in [main.tf](./iaac/admin/main.tf) 

### Setup Git Repository, Admin Service Account & Cloud Build

We want to maintain the infrastructure as Code, for this we will setup a Git Repo and check-in all our changes in this
repository. We would also like to setup an automated build process that will trigger when a new change is available on a
given git branch. We will follow the steps as mentioned below

* Configure a new Git repository `gcp-learning`
* Enable `Cloud Build Api`
* Create a new service account and name it as `DevOps Admin Service Account`
  This service account would be used by the cloud build to create any other infrastructure item like network, compute
  nodes, service account, grant roles etc. We will assign following roles to this service Account.

  Role | Role Name| What this role can do| API required |
  ---- |--------- |--------------------- |------------- |
  roles/cloudbuild.builds.builder| Cloud Build Service Account | Can perform builds | Cloud Build API |
  roles/iam.serviceAccountAdmin  | Service Account Admin | Create and manage Service Account | |
  roles/iam.securityAdmin        | Security Admin | Security admin role, with permissions to get and set any IAM policy. | Cloud Resource Manager API |
  roles/compute.networkAdmin     | Compute Network Admin| Full control of Compute Engine networking resources.| |	

* Setup a build trigger
  * Connect Git Repository to Cloud Build
    * Create a build trigger
      Setup branch and the included and excluded resources to trigger a build
      Use the Devops Admin Service Account to trigger the build