#!/bin/bash

tag=aksregistryprod.azurecr.io/cp-kafka-connect:4.1.0_eventhub_azkv
read -p "Enter Nexus Username: " user
read -sp "Enter Nexus Password: " password
echo ""

sbt clean assembly && \
docker build --build-arg NEXUS_USERNAME=$user --build-arg NEXUS_PASSWORD=$password . --no-cache -t $tag && \
docker push $tag && \
echo "Done! Enjoy..." || echo "ERROR! Check logs..."
