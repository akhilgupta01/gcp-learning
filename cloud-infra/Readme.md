Building Cloud Infra (Using Terraform)
===
In this module we will create a GCP project from scratch and then create some infrastructure components using 
[Terraform](https://www.terraform.io/).

Each directory under this module represents a project in GCP environment.
The terraform configuration files and any source code (for e.g. cloud function code) is present as a sub project sub-directories  

## 1. Starting Point

As a starting point, you are required to create a new GCP project.
You can use a Free Tier Account to try out the examples provided in this module.
Let us assume that we created a project with id `ag-trial-project-a` (Please note that Project Id can be different from Project name)

## 2. Setting up Terraform CI/CD

In order to be able to compare the requested infrastructure with the existing state, Terraform requires a storage space 
where it can maintain the current state of the infrastructure. On GCP environment, we can provide this space as a storage 
bucket on `Google Cloud Storage`. 

So, let us start by creating a storage space for Terraform

### 2.1) Create a new Storage Bucket

* Enable the `Cloud Storage API`. GCP provides various ways to do this (viz. GCP Console, API or Cloud Shell). 
  As GCP Console is the easiest for a beginner, so you can use this method to enable the API
* Create a new Cloud Storage bucket, lets call it as `ag-trial-project-a-tfstate`
* Refer to this bucket as the backend bucket in [main.tf](./project1/terraform/main.tf). 
  
Note: If you choose to provide a different name for your terraform storage bucket, you will need to modify the terraform configuration files to reflect that.

### 2.2) Setup Admin Service Account

When you create a new project, your user by default gets the `Owner` privileges on the project.
In order to properly understand the permissions required to use a GCP service, we will not use this user to build the infrastructure.
We will instead create a new `Service Account` and give it the necessary permissions based on our use case requirement.

We will use this service account to trigger the cloud builds that will run the terraform commands to build the infra. 

Please follow these steps to create this Service Account:

* On the GCP Console, go to `IAM & Admin` > `Service Accounts` page
* Click on `+ CREATE SERVICE ACCOUNT` link at the top
* Create a new service account and name it as `admin-sa` and description as `DevOps Admin Service Account`
* Then go to the `IAM & Admin` > `IAM` page, and edit the `admin-sa` principal to add following permissions
 
Role | Role Name| What this role can do| API required |
---- |--------- |--------------------- |------------- |
roles/cloudbuild.builds.builder| Cloud Build Service Account | Can perform builds | Cloud Build API |
 

### 2.3) Create a new Cloud Build Trigger 

Using Cloud Build Trigger, we would also like to setup an automated build process that will trigger a CI/CD pipeline when 
a new change is available on a given git branch. Please follow following steps to setup a build trigger.

* Go to `Cloud Build` > `Triggers` page from the main menu
* Provide any name that you like for this build trigger
* In the Source `Repository` section, select `CONNECT NEW REPOSITORY`, this will open a new form on the right hand side 
* Select `GitHub (legacy BETA` option and connect to this git repository
* Provide the branch that you want to use for the builds
* Use `cloud-infra/project1/**` as included files filter
* In the Configuration section, select type as `Cloud Build configuration file`
* Specify cloud build configuration file location as `cloud-infra/project1/terraform/cloudbuild.yaml`
* At the bottom of this page, select Service Account email as `admin-sa@<project id>.iam.gserviceaccount.com`
* Save to complete setting up the Build Trigger

You can now click on the `RUN` link to manually trigger this cloud run build.

## 3) Additional permissions required per use case
You will notice that the terraform module contains several .tf files.

Each of these terraform configuration files requires to enable a specific GCP service and also a specific permission to 
be granted to the `admin-sa` account to build the instance of that service.
The table below lists the various services that are required to be enabled along with the required permissions per use case.

Use Case | Terraform Config | API required | Role | Role Name| What this role can do?|
-------- |----------------- |------------- |----- |--------- |---------------------- |
Create a new Network        | [network.tf](./project1/terraform/network.tf) | N/A |roles/compute.networkAdmin  | Compute Network Admin| Full control of Compute Engine networking resources.|
Create new Service Accounts | [service_accounts.tf](./project1/terraform/service_accounts.tf) | Identity and Access Management (IAM) API |roles/iam.serviceAccountAdmin  | Service Account Admin | Create and manage Service Account |

[comment]: <> (  roles/cloudbuild.builds.builder| Cloud Build Service Account | Can perform builds | Cloud Build API |)

[comment]: <> (   |)

[comment]: <> (  roles/iam.securityAdmin        | Security Admin | Security admin role, with permissions to get and set any IAM policy. | Cloud Resource Manager API |)
  
[comment]: <> (  roles/bigquery.admin     | BigQuery Admin| Full control of BigQuery.| BigQuery API|)

[comment]: <> (  roles/pubsub.admin             | Pub/Sub Admin| Full control of Pub Sub.|PubSub API |)

[comment]: <> (* roles/cloudfunctions.admin             | Cloud Functions Admin| Full control of Cloud Functions.|Cloud Function API |)

[comment]: <> (* roles/iam.serviceAccountUser             | Service Account User| | |)
