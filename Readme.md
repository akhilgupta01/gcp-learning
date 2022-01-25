GCP Learning
===
In this module we will try out various components of GCP, with the help of some use cases.
We will start with a totally new project, so that we can understand what all resources are required in order to achieve our use case.
You can start by creating a new free trial account on GCP.
Let us assume that we created a project with name `ag-gcp-learning`

### Introducing Terraform
GCP offers several ways to create and configure resources on it platform, viz. UI, APIs, Cloud Shell etc.
While the UI based approach is convenient, it is not suitable for repeatability and standardization. 
For e.g. what if you want to create the same resource across multiple projects?

For our demonstration purpose, we will take the IAAC approach, so that we can recreate our environment in a new project area.
Moreover, it gives us a very clear understanding of various GCP resources and their associated dependencies.

For IAAC, we will use Terraform, Terraform provides us the capability to capture our infrastructure details in some configuration files.
These configuration files can be also version controlled, which in turns facilitates version controlling our infrastructure.

Whenever a new version of Terraform configuration is requested for deployment, Terraform compares the actual state of the environment with the newly requested configuration.
It modifies only the resources that have changed and does not touch the resources that remain the same.

In order to be able to compare the requested configuration with the existing state, Terraform requires a storage space where it can maintain the current state of the infrastructure.
We will provide this space on `Google Cloud Storage`. So, let us first start by creating a storage space for Terraform

As this is a pre-requisite for Terraform, we will have to use one of the GCP provides methods like UI, API or Cloud Shell to create the storage bucket.

####Steps to create Cloud Storage Bucket for Terraform 
* Enable the `Cloud Storage API`
* Create a new Cloud Storage bucket, lets call it as `ag-gcp-learning-tfstate-adm`
* Refer to this bucket as the backend bucket in [main.tf](cloud-infra/project1/terraform/main.tf) 

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