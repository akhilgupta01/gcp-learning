import google.cloud.compute_v1 as compute_v1
import json

def manage_vm_instance(message, event):
    print(message)
    print(message['data'])
    print(message.data)
    data_json = json.loads(message.data.decode('utf-8'))
    project_id = data_json['project_id']
    zone = data_json['zone']
    vm_instance = data_json['vm_instance']
    action = data_json['action']
    instance_client = compute_v1.InstancesClient()
    instance_list = instance_client.list(project=project_id, zone=zone)
    if action == 'start':
        print(f"Received request to start compute instance {vm_instance}")
        for instance in instance_list:
            print(f"Starting instance - {instance.name}")
            instance_client.start_unary(project=project_id, zone=zone, instance=instance.name)
    elif action == 'stop':
        print(f"Received request to stop compute instance {vm_instance}")
        for instance in instance_list:
            print(f"Stopping instance - {instance.name}")
            instance_client.stop_unary(project=project_id, zone=zone, instance=instance.name)
    return "OK"
