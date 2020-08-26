# KAFKA LOCALHOST COMMANDS

You do not need to use any of these commands if you spin up the Kafka Broker and Zookeeper Docker containers, but they are here for your reference!

### CREATE KAFKA TOPIC - If it does not already exist
sh bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 
--partitions 1 --topic users

### START ZOOKEEPER
sh bin/zookeeper-server-start.sh config/zookeeper.properties

### START KAFKA BROKER
sh bin/kafka-server-start.sh config/server.properties

### START KAFKA CONSOLE CONSUMER - Where you will see the kafka messages sent to the topic
sh bin/kafka-console-consumer.sh --topic users --from-beginning --bootstrap-server localhost:9092

### START KAFKA CONSOLE PRODUCER - If you want to send messages through the console instead of the app
sh bin/kafka-console-producer.sh --broker-list localhost:9092 --topic users

### CURL COMMAND TO HIT THE APP WITH A MESSAGE
curl -X POST http://localhost:9000/kafka/publish?message=myMessage
