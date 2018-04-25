FROM aksregistryprod.azurecr.io/cp-kafka-connect:4.1.0

RUN mkdir -p /usr/share/java/kafka-connect-eventhub
COPY target/scala-2.11/kafka-connect-iothub-assembly*.jar /usr/share/java/kafka-connect-eventhub/

RUN mkdir -p /my_docker
COPY Dockerfile /my_docker/
COPY build.sh /my_docker/
