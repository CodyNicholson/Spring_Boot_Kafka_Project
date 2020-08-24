# Spring Boot Kafka Project

This is an exploratory project I put together to practice integrating kafka with spring boot. I also containerized both Zookeeper and the Kafka Broker using docker.

To run the docker compose file in the background, cd into the docker directory and run:

```
docker-compose up -d
```

NOTE: I built this project using Windows and Docker Toolbox. When using Windows and Docker Toolbox you have to point to "192.168.99.100" instead of "localhost" in the application.yml file.

Once you have docker running correctly and you spin up the app you can hit the KafkaController endpoint with this curl command:

```
curl -X POST http://localhost:9000/kafka/publish?message=myMessage
```

You should then see whatever message you provided appear in the logs twice. Once for when it was Produced, and once for when it was Consumed.
