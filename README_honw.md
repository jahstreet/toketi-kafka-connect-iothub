```bash
curl -X POST -H "Content-Type: application/json" \
  --data '{"name": "<connector_name>",
           "config": {
             "connector.class":"com.microsoft.azure.iot.kafka.connect.IotHubSourceConnector",
             "Kafka.Topic":"<topic_name>",
             "tasks.max":"1",
             "IotHub.EventHubCompatibleName":"<evnethub_name>",
             "IotHub.EventHubCompatibleEndpoint":"sb://<placeholder>.servicebus.windows.net/",
             "IotHub.AccessKeyName":"<access_key_name>",
             "IotHub.AccessKeyValue":"<access_key_value",
             "IotHub.ConsumerGroup":"<consumer_group>",
             "IotHub.Partitions":"<eventhub_partitions_total>",
             "BatchSize":"10", #for each partitions on every fetch request
             "ReceiveTimeout":"60" #time based batching limit sec
             }
          }' kafka-connect-worker:8083/connectors
```