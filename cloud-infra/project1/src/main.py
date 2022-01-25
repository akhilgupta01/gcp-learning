import google.cloud.compute_v1 as compute_v1
import base64
import json


def manage_vm_instance(event, context):
    if 'data' in event:
        message = base64.b64decode(event['data']).decode('utf-8')
        print(message)
        message_json = json.loads(message)
        print(message_json)
        print(message_json['project_id'])
        project_id = message_json['project_id']
        zone = message_json['zone']
        vm_instance = message_json['vm_instance']
        action = message_json['action']
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
    else:
        print("No data found in event")
