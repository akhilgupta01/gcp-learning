import google.cloud.compute_v1 as compute_v1

def startInstancePubSub(request):
    instance_client = compute_v1.InstancesClient()
    instance_list = instance_client.list(project='ag-trial-project-1', zone='us-central1-a')
    for instance in instance_list:
        print(f" - {instance.name} ({instance.machine_type})")

    return instance_list