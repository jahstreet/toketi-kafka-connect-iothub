#!/bin/bash

sbt clean assembly && \
docker build . --no-cache -t aksregistryprod.azurecr.io/cp-kafka-connect:4.1.0_eventhub && \
docker push aksregistryprod.azurecr.io/cp-kafka-connect:4.1.0_eventhub && \
echo "Done! Enjoy..." || echo "ERROR! Check logs..."
