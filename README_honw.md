```bash
# Basic credential providing
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

# Azure Key Vault credential providing
curl -X POST -H "Content-Type: application/json" \
  --data '{"name": "<connector_name>",
           "config": {
             "connector.class":"com.microsoft.azure.iot.kafka.connect.source.IotHubSourceConnector",
             "Kafka.Topic":"<topic_name>",
             "tasks.max":"1",
             "azkv.url":"https://<key_vault>.vault.azure.net/",
             "eventhubs.name.secret":"eventhubs-name",
             "eventhubs.namespace.secret":"eventhubs-namespace",
             "eventhubs.policyname.secret":"eventhubs-policyname",
             "eventhubs.policykey.secret":"eventhubs-policykey",
             "eventhubs.consumergroup.secret":"eventhubs-consumergroup",
             "IotHub.Partitions":"<eventhub_partitions_total>",
             "BatchSize":"100", #for each partitions on every fetch request
             "ReceiveTimeout":"60" #time based batching limit sec
             }
          }' kafka-connect-worker:8083/connectors
```
