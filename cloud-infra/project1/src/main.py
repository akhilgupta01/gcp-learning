import google.cloud.compute_v1 as compute_v1

def startInstancePubSub(request, event):
    instance_client = compute_v1.InstancesClient()
    print("Received request to start compute instance ", request)
    projectId = 'ag-trial-project-1'
    zone = 'us-central1-a'

    instance_list = instance_client.list(project=projectId, zone=zone)
    for instance in instance_list:
        print(f" - {instance.name} ({instance.machine_type})")
        instance_client.start_unary(project=projectId, zone=zone, instance=instance.name)

    return instance_list