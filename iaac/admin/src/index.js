const compute = require('@google-cloud/compute');
const instancesClient = new compute.InstancesClient({fallback: 'rest'});

/**
 * Starts Compute Engine instances.
 *
 * Expects a PubSub message with JSON-formatted event data containing the
 * following attributes:
 *  zone - the GCP zone the instances are located in.
 *  label - the label of instances to start.
 *
 * @param {!object} event Cloud Function PubSub message event.
 * @param {!object} callback Cloud Function PubSub callback indicating
 *  completion.
 */
exports.startInstancePubSub = async (event, context, callback) => {
  try {
    const project = await instancesClient.getProjectId();
    const payload = _validatePayload(event);
    const options = {
      filter: `labels.${payload.label}`,
      project,
      zone: payload.zone,
    };

    const [instances] = await instancesClient.list(options);

    if (instances.items) {
      await Promise.all(
        instances.items.map(async instance => {
          return instancesClient.start({
            project,
            zone: payload.zone,
            instance: instance.name,
          });
        })
      );
    }

    // Operation complete. Instance successfully started.
    const message = 'Successfully started instance(s)';
    console.log(message);
    callback(null, message);
  } catch (err) {
    console.log(err);
    callback(err);
  }
};

const _validatePayload = event => {
  let payload;
  try {
    payload = JSON.parse(Buffer.from(event.data, 'base64').toString());
  } catch (err) {
    throw new Error('Invalid Pub/Sub message: ' + err);
  }
  if (!payload.zone) {
    throw new Error("Attribute 'zone' missing from payload");
  } else if (!payload.label) {
    throw new Error("Attribute 'label' missing from payload");
  }
  return payload;
};
