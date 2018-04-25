// Copyright (c) Microsoft. All rights reserved.

package com.microsoft.azure.iot.kafka.connect.source

import java.time.Instant
import java.util.Date

import com.microsoft.azure.eventhubs.impl.AmqpConstants
import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

import scala.collection.JavaConverters._
import scala.reflect.ClassTag

object IotMessageConverter {

  // Public for testing purposes
  lazy val schema: Schema = SchemaBuilder.struct()
                            .name(schemaName)
                            .version(schemaVersion)
                            .field(deviceIdKey, Schema.STRING_SCHEMA)
                            .field(offsetKey, Schema.STRING_SCHEMA)
                            .field(contentTypeKey, Schema.OPTIONAL_STRING_SCHEMA)
                            .field(enqueuedTimeKey, Schema.INT64_SCHEMA)
                            .field(sequenceNumberKey, Schema.INT64_SCHEMA)
                            .field(contentKey, Schema.STRING_SCHEMA)
                            .field(systemPropertiesKey, propertiesMapSchema)
                            .field(propertiesKey, propertiesMapSchema)

  // TODO made optionals
  private lazy val propertiesMapSchema: Schema = SchemaBuilder.map(Schema.OPTIONAL_STRING_SCHEMA, Schema.OPTIONAL_STRING_SCHEMA)

  val offsetKey = "offset"

  private val schemaName          = "iothub.kafka.connect"
  private val schemaVersion       = 1
  private val deviceIdKey         = "deviceId"
  private val contentTypeKey      = "contentType"
  private val sequenceNumberKey   = "sequenceNumber"
  private val enqueuedTimeKey     = "enqueuedTime"
  private val contentKey          = "content"
  private val systemPropertiesKey = "systemProperties"
  private val propertiesKey       = "properties"
  private val deviceIdIotHubKey   = "iothub-connection-device-id"

  def getIotMessageStruct(iotMessage: IotMessage): Struct = {

    val systemProperties = iotMessage.systemProperties
    val deviceId: String = getOrDefault(systemProperties, deviceIdIotHubKey, "")
    val offset: String = getOrDefault(systemProperties, AmqpConstants.OFFSET_ANNOTATION_NAME, "")
    val sequenceNumber: Long = getOrDefault(systemProperties, AmqpConstants.SEQUENCE_NUMBER_ANNOTATION_NAME, 0)
    val enqueuedTime: Option[Instant] = getEnqueuedTime(systemProperties)
    val enqueuedTimeLong = if (enqueuedTime.isDefined) enqueuedTime.get.getEpochSecond else 0

    val properties = iotMessage.properties
    val contentType: String = getOrDefault(properties, contentTypeKey, "")

    val systemPropertiesMap = systemProperties.map(i => (i._1, i._2.toString))

    new Struct(schema)
      .put(deviceIdKey, deviceId)
      .put(offsetKey, offset)
      .put(contentTypeKey, contentType)
      .put(enqueuedTimeKey, enqueuedTimeLong)
      .put(sequenceNumberKey, sequenceNumber)
      .put(contentKey, iotMessage.content)
      .put(systemPropertiesKey, systemPropertiesMap.asJava)
      .put(propertiesKey, properties.asJava)
  }

  private def getEnqueuedTime(map: scala.collection.mutable.Map[String, Object]): Option[Instant] = {
    val enqueuedTimeValue: Date = getOrDefault(map, AmqpConstants.ENQUEUED_TIME_UTC_ANNOTATION_NAME, null)
    if (enqueuedTimeValue != null) Some(enqueuedTimeValue.toInstant) else None
  }

  private def getOrDefaultAndRemove[T: ClassTag, S: ClassTag](map: scala.collection.mutable.Map[String, S],
                                                              key: String, defaultVal: T): T =
  {

    if (map.contains(key)) {
      val retVal: T = map(key).asInstanceOf[T]
      map.remove(key)
      retVal
    } else
    {
      defaultVal
    }
  }

  private def getOrDefault[T: ClassTag, S: ClassTag](map: scala.collection.mutable.Map[String, S],
                                                     key: String, defaultVal: T): T =
  {

    if (map.contains(key)) {
      map(key).asInstanceOf[T]
    } else
    {
      defaultVal
    }
  }
}