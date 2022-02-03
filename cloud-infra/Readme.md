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

## 2. Setting up Terraform

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
 


### 3. Setup Admin Service Account, Cloud Build Trigger 
We want to maintain the infrastructure as Code, for this we will setup a Git Repo and check-in all our changes in this
repository. We would also like to setup an automated build process that will trigger when a new change is available on a
given git branch. We will follow the steps as mentioned below

* Configure a new Git repository `gcp-learning`
* Enable `Cloud Build Api` and `Identity and Access Management (IAM) API`
* Create a new service account and name it as `DevOps Admin Service Account`
  This service account would be used by the cloud build to create any other infrastructure item like network, compute
  nodes, service account, grant roles etc. We will assign following roles to this service Account.

  Role | Role Name| What this role can do| API required |
  ---- |--------- |--------------------- |------------- |
  roles/cloudbuild.builds.builder| Cloud Build Service Account | Can perform builds | Cloud Build API |
  roles/iam.serviceAccountAdmin  | Service Account Admin | Create and manage Service Account | |
  roles/iam.securityAdmin        | Security Admin | Security admin role, with permissions to get and set any IAM policy. | Cloud Resource Manager API |
  roles/compute.networkAdmin     | Compute Network Admin| Full control of Compute Engine networking resources.| |
  roles/bigquery.admin     | BigQuery Admin| Full control of BigQuery.| BigQuery API|
  roles/pubsub.admin             | Pub/Sub Admin| Full control of Pub Sub.|PubSub API |
* roles/cloudfunctions.admin             | Cloud Functions Admin| Full control of Cloud Functions.|Cloud Function API |
* roles/iam.serviceAccountUser             | Service Account User| | |

* Setup a build trigger
  * Connect Git Repository to Cloud Build
    * Create a build trigger
      Setup branch and the included and excluded resources to trigger a build
      Use the Devops Admin Service Account to trigger the build

## word-count in Dataflow
#### Useful Links
- Creating Dataflow Templates 
  - https://cloud.google.com/dataflow/docs/guides/templates/creating-templates


Use following command to create a template
```
mvn compile exec:java -Dexec.mainClass=com.examples.beam.tx.TxReportDataflowRunner \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args="--runner=DataflowRunner \
--project=ag-trial-project1-a \
--stagingLocation=gs://ag-trial-project-a_work_dir/staging \
--tempLocation=gs://ag-trial-project-a_work_dir/working \
--templateLocation=gs://ag-trial-project-a_work_dir/templates/tx_report_job_template \
--region=us-central1"
```

Use following command to create a Job from a template
```
gcloud dataflow jobs run txreport-1 \
--gcs-location gs://ag-trial-project-a_work_dir/templates/tx_report_job_template \
--parameters transactionFile=gs://ag-trial-project-a_work_dir/incoming/transactions.csv \
--region us-central1 \
--staging-location gs://ag-trial-project-a_work_dir/working
```
