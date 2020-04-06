#!/bin/bash

# Enter topic name
if [ "$#" -ne 1 ]; then
	echo "Please supply topic name"
   	exit 1
fi


# Launch Zookeeper server in background
bin/zookeeper-server-start.sh -daemon config/zookeeper.properties > zookeeper.log 2>&1 &
sleep 2

echo "Zookeeper launched"

# Launch 3 kafka brokers
bin/kafka-server-start.sh -daemon config/server.properties 2>&1 &  
sleep 2

#bin/kafka-server-start.sh -daemon config/server-1.properties 2>&1 &  
#sleep 2

#bin/kafka-server-start.sh -daemon config/server-2.properties 2>&1 &  
#sleep 2
echo "Kafka cluster launched"

# Open new terminal then create new topic
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic $1
sleep 2

echo "Kafka setup finished"
