# Spring Boot Kafka Project

This is an exploratory project I put together to practice integrating kafka with spring boot. I also containerized both Zookeeper and the Kafka Broker using docker.

To run the docker compose file in the background, cd into the relevant docker directory and run:

```
docker-compose up -d
```

NOTE: I built this project using Windows and Docker Toolbox. When using Windows and Docker Toolbox you have to point to "192.168.99.100" instead of "localhost" in the application.yml file and also in the docker-compose file. To facilitate this behavior, I added a DOCKER_TOOLBOX spring profile and docker-toolbox docker-compose file that work with Docker Toolbox, and I made the default spring profile and normal docker-compose file work for the normal Docker that I'm sure most people have.

Once you have docker running correctly you can spin up the app and hit the KafkaController endpoint with this curl command:

```
curl -X POST http://localhost:9000/kafka/publish?message=myMessage
```

You should then see whatever message you provided appear in the logs twice. Once for when it was Produced, and once for when it was Consumed.
