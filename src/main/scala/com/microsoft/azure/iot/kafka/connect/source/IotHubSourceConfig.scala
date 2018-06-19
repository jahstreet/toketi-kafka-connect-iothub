// Copyright (c) Microsoft. All rights reserved.

package com.microsoft.azure.iot.kafka.connect.source

import com.microsoft.azure.eventhubs.EventHubClient
import org.apache.kafka.common.config.ConfigDef.{Importance, Type, Width}
import org.apache.kafka.common.config.{AbstractConfig, ConfigDef}

import java.util.Map

object IotHubSourceConfig {

  private val defaultBatchSize = 100
  private val defaultReceiveTimeout = 60
  private val defaultEmptyString = ""
  private val iotConfigGroup   = "Azure IoT Hub"
  private val azkvConfigGroup  = "Azure Key Vault"
  private val kafkaConfig      = "Kafka"

  val EventHubCompatibleConnectionString = "IotHub.EventHubCompatibleConnectionString"
  val EventHubCompatibleName             = "IotHub.EventHubCompatibleName"
  val EventHubCompatibleNameDoc          =
    """EventHub compatible name ("IoT Hub" >> your hub >> "Endpoints" >> "Events" >> "Event Hub-compatible name")"""
  val EventHubCompatibleEndpoint        = "IotHub.EventHubCompatibleEndpoint"
  val EventHubCompatibleEndpointDoc     =
    """EventHub compatible endpoint ("IoT Hub" >> your hub >> "Endpoints" >> "Events" >> "Event Hub-compatible """ +
      """endpoint")"""
  val IotHubAccessKeyName                = "IotHub.AccessKeyName"
  val IotHubAccessKeyNameDoc             =
    """IotHub access key name ("IoT Hub" >> your hub >> "Shared access policies", default is service)"""
  val IotHubAccessKeyValue               = "IotHub.AccessKeyValue"
  val IotHubAccessKeyValueDoc            =
    """IotHub access key value ("IoT Hub" >> your hub >> "Shared access policies" >> key name >> "Primary key")"""
  val IotHubConsumerGroup                = "IotHub.ConsumerGroup"
  val IotHubConsumerGroupDoc             = "The IoT Hub consumer group"
  val IotHubPartitions                   = "IotHub.Partitions"
  val IotHubPartitionsDoc                = "Number of IoT Hub partitions"
  val KafkaTopic                         = "Kafka.Topic"
  val KafkaTopicDoc                      = "Kafka topic to copy data to"
  val BatchSize                          = "BatchSize"
  val BatchSizeDoc                       = "The batch size for fetching records from IoT Hub"
  val ReceiveTimeout                     = "ReceiveTimeout"
  val ReceiveTimeoutDoc                  = "Max time to spend receiving messages from IoT Hub"
  val IotHubOffset                       = "IotHub.Offsets"
  val IotHubOffsetDoc                    =
    "Offset for each partition in IotHub, as a comma separated string. This value is ignored if IotHubStartTime is specified."
  val IotHubStartTime                    = "IotHub.StartTime"
  val IotHubStartTimeDoc                 = "The time after which to process messages from IoT Hub If this value " +
    "is specified, IotHubOffset value is ignored."
  val TaskPartitionOffsetsMap            = "TaskPartitions"

  val AzureKeyVaultUrl                   = "azkv.url"
  val AzureKeyVaultUrlDoc                = "Azure Key Vault URL to retrieve EventHub configs"
  val EventHubNameSecret                 = "eventhubs.name.secret"
  val EventHubNameSecretDoc              = "Azure Key Vault secret name for EventHub name"
  val EventHubNamespaceSecret            = "eventhubs.namespace.secret"
  val EventHubNamespaceSecretDoc         = "Azure Key Vault secret name for EventHub namespace"
  val EventHubPolicynameSecret           = "eventhubs.policyname.secret"
  val EventHubPolicynameSecretDoc        = "Azure Key Vault secret name for EventHub policyname"
  val EventHubPolicykeySecret            = "eventhubs.policykey.secret"
  val EventHubPolicykeySecretDoc         = "Azure Key Vault secret name for EventHub policykey"
  val EventHubConsumergroupSecret        = "eventhubs.consumergroup.secret"
  val EventHubConsumergroupSecretDoc     = "Azure Key Vault secret name for EventHub consumergroup"

  lazy val configDef = new ConfigDef()
    .define(EventHubCompatibleName, Type.STRING, defaultEmptyString, Importance.HIGH, EventHubCompatibleNameDoc, iotConfigGroup, 1, Width
      .MEDIUM, "Event Hub compatible name")
    .define(EventHubCompatibleEndpoint, Type.STRING, defaultEmptyString, Importance.HIGH, EventHubCompatibleEndpointDoc,
      iotConfigGroup, 2, Width.MEDIUM, "Event Hub compatible endpoint")
    .define(IotHubAccessKeyName, Type.STRING, defaultEmptyString, Importance.HIGH, IotHubAccessKeyNameDoc, iotConfigGroup, 3, Width.SHORT,
      "Access key name")
    .define(IotHubAccessKeyValue, Type.STRING, defaultEmptyString, Importance.HIGH, IotHubAccessKeyValueDoc, iotConfigGroup, 4,
      Width.LONG, "Access key value")
    .define(IotHubConsumerGroup, Type.STRING, EventHubClient.DEFAULT_CONSUMER_GROUP_NAME, Importance.MEDIUM,
      IotHubConsumerGroupDoc, iotConfigGroup, 5, Width.SHORT, "Consumer group")
    .define(IotHubPartitions, Type.INT, Importance.HIGH, IotHubPartitionsDoc, iotConfigGroup, 6, Width.SHORT,
      "IoT Hub partitions")
    .define(IotHubStartTime, Type.STRING, defaultEmptyString, Importance.MEDIUM, IotHubStartTimeDoc, iotConfigGroup, 7, Width.MEDIUM,
      "Start time")
    .define(IotHubOffset, Type.STRING, defaultEmptyString, Importance.MEDIUM, IotHubOffsetDoc, iotConfigGroup, 8, Width.MEDIUM,
      "Per partition offsets")
    .define(BatchSize, Type.INT, defaultBatchSize, Importance.MEDIUM, IotHubOffsetDoc, iotConfigGroup, 9, Width.SHORT,
      "Batch size")
    .define(ReceiveTimeout, Type.INT, defaultReceiveTimeout, Importance.MEDIUM, ReceiveTimeoutDoc, iotConfigGroup, 10,
      Width.SHORT, "Receive Timeout")
    .define(KafkaTopic, Type.STRING, Importance.HIGH, KafkaTopicDoc, kafkaConfig, 11, Width.MEDIUM, "Kafka topic")
    .define(AzureKeyVaultUrl, Type.STRING, defaultEmptyString, Importance.HIGH, AzureKeyVaultUrlDoc, azkvConfigGroup,
      12, Width.MEDIUM, "Azure Key Vault URL")
    .define(EventHubNameSecret, Type.STRING, defaultEmptyString, Importance.HIGH, EventHubNameSecretDoc, azkvConfigGroup,
      13, Width.MEDIUM, "Azure Key Vault secret name for EventHub name")
    .define(EventHubNamespaceSecret, Type.STRING, defaultEmptyString, Importance.HIGH, EventHubNamespaceSecretDoc,
      azkvConfigGroup, 14, Width.MEDIUM, "Azure Key Vault secret name for EventHub namespace")
    .define(EventHubPolicynameSecret, Type.STRING, defaultEmptyString, Importance.HIGH, EventHubPolicynameSecretDoc,
      azkvConfigGroup, 15, Width.MEDIUM, "Azure Key Vault secret name for EventHub policyname")
    .define(EventHubPolicykeySecret, Type.STRING, defaultEmptyString, Importance.HIGH, EventHubPolicykeySecretDoc,
      azkvConfigGroup, 16, Width.MEDIUM, "Azure Key Vault secret name for EventHub policykey")
    .define(EventHubConsumergroupSecret, Type.STRING, defaultEmptyString, Importance.HIGH, EventHubConsumergroupSecretDoc,
      azkvConfigGroup, 17, Width.MEDIUM, "Azure Key Vault secret name for EventHub consumergroup")

  def getConfig(configValues: Map[String, String]): IotHubSourceConfig = {
    new IotHubSourceConfig(configDef, configValues)
  }
}

class IotHubSourceConfig(configDef: ConfigDef, configValues: Map[String, String])
  extends AbstractConfig(configDef, configValues)
