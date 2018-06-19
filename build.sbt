import scala.collection.Seq
// Copyright (c) Microsoft. All rights reserved.

val iotHubKafkaConnectVersion = "0.7.0"

name := "kafka-connect-iothub"
organization := "com.microsoft.azure.iot"
version := iotHubKafkaConnectVersion

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-deprecation", "-explaintypes", "-unchecked", "-feature")

libraryDependencies ++= {

  val kafkaVersion = "0.10.2.1"
  val azureEventHubSDKVersion = "1.0.0"
  val scalaLoggingVersion = "3.5.0"
  val logbackClassicVersion = "1.1.7"
  val scalaTestVersion = "3.0.0"
  val configVersion = "1.3.2"
  val json4sVersion = "3.5.0"
  val iotHubServiceClientVersion = "1.4.22"
  val azureVersion = "1.3.0"
  val azureKeyVaultVersion = "1.0.0"
  val azureKeyVaultProviderVersion = "1.0.1"
  val hadoopVersion = "3.0.0"

  Seq(
    "org.apache.kafka" % "connect-api" % kafkaVersion % "provided",
    "org.apache.kafka" % "connect-json" % kafkaVersion % "provided",
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion % "provided",
    "com.microsoft.azure" % "azure-eventhubs" % azureEventHubSDKVersion,
    "org.json4s" %% "json4s-jackson" % json4sVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "com.microsoft.azure.sdk.iot" % "iot-service-client" % iotHubServiceClientVersion,

    // Azure Key Vault Dependencies
    "com.microsoft.azure" % "azure-keyvault-core" % azureKeyVaultVersion % "provided",
    "com.epam.honw" % "honw-key-vault-provider" % azureKeyVaultProviderVersion % "provided",
    "org.apache.hadoop" % "hadoop-azure" % hadoopVersion % "provided"
      exclude("com.microsoft.azure", "azure-storage")
      exclude("javax.servlet", "servlet-api")
      exclude("javax.servlet.jsp", "jsp-api")
      exclude("org.mortbay.jetty", "servlet-api"),

    // Test dependencies
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
    "com.typesafe" % "config" % configVersion % "test",
    "com.microsoft.azure" % "azure" % azureVersion % "test",
    "com.microsoft.azure" % "azure-keyvault" % azureKeyVaultVersion % "test"
  )
}

resolvers in ThisBuild ++= Seq(
  MavenRepository("Sonatype Nexus Repository Manager", "https://lca-dl-dev-artifactory.eastus2.cloudapp.azure.com/repository/honw_analytics_cluster_libs/"),
  Resolver.mavenLocal
)

credentials += Credentials(Path.userHome / ".ivy2" / ".my-credentials")

assemblyJarName in assembly := s"kafka-connect-iothub-assembly_2.11-$iotHubKafkaConnectVersion.jar"

publishArtifact in Test := true
publishArtifact in(Compile, packageDoc) := true
publishArtifact in(Compile, packageSrc) := true
publishArtifact in(Compile, packageBin) := true

fork in run := true

licenses += ("MIT", url("https://github.com/Azure/toketi-kafka-connect-iothub/blob/master/LICENSE"))
publishMavenStyle := true

// Bintray: Organization > Repository > Package > Version
bintrayOrganization := Some("microsoftazuretoketi")
bintrayRepository := "toketi-repo"
bintrayPackage := "kafka-connect-iothub"
bintrayReleaseOnPublish in ThisBuild := true

// Required in Sonatype
pomExtra :=
    <url>https://github.com/Azure/toketi-kafka-connect-iothub</url>
    <scm><url>https://github.com/Azure/toketi-kafka-connect-iothub</url></scm>
    <developers><developer><id>microsoft</id><name>Microsoft</name></developer></developers>
