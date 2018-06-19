FROM aksregistryprod.azurecr.io/cp-kafka-connect:4.1.0

# pass with `--build-arg NEXUS_USERNAME=<user> --build-arg NEXUS_PASSWORD=<password>` on build
ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD

ARG EVENTHUB_CONNECTOR_DIR=/usr/share/java/kafka-connect-eventhub
ARG MY_DOCKER_DIR=/my_docker

ARG HADOOP_VERSION=3.0.0
ARG KEY_VAULT_VERSION=1.0.0
ARG KEY_VAULT_PROVIDER_VERSION=1.0.1

RUN mkdir -p $EVENTHUB_CONNECTOR_DIR
COPY target/scala-2.11/kafka-connect-iothub-assembly*.jar $EVENTHUB_CONNECTOR_DIR/
RUN wget -q --no-check-certificate -O $EVENTHUB_CONNECTOR_DIR/honw-key-vault-provider-$KEY_VAULT_PROVIDER_VERSION.jar https://$NEXUS_USERNAME:$NEXUS_PASSWORD@lca-dl-dev-artifactory.eastus2.cloudapp.azure.com/repository/honw_analytics_cluster_libs/com/epam/honw/honw-key-vault-provider/$KEY_VAULT_PROVIDER_VERSION/honw-key-vault-provider-$KEY_VAULT_PROVIDER_VERSION.jar && \
    wget -q -O $EVENTHUB_CONNECTOR_DIR/azure-keyvault-core-$KEY_VAULT_VERSION.jar http://central.maven.org/maven2/com/microsoft/azure/azure-keyvault-core/$KEY_VAULT_VERSION/azure-keyvault-core-$KEY_VAULT_VERSION.jar && \
    wget -q -O $EVENTHUB_CONNECTOR_DIR/hadoop-azure-datalake-$HADOOP_VERSION.jar http://central.maven.org/maven2/org/apache/hadoop/hadoop-azure-datalake/$HADOOP_VERSION/hadoop-azure-datalake-$HADOOP_VERSION.jar && \
    wget -q -O $EVENTHUB_CONNECTOR_DIR/hadoop-common-$HADOOP_VERSION.jar http://central.maven.org/maven2/org/apache/hadoop/hadoop-common/$HADOOP_VERSION/hadoop-common-$HADOOP_VERSION.jar

RUN mkdir -p $MY_DOCKER_DIR
COPY Dockerfile $MY_DOCKER_DIR/
COPY build.sh $MY_DOCKER_DIR/
