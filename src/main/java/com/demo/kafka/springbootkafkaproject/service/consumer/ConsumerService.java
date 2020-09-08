package com.demo.kafka.springbootkafkaproject.service.consumer;

import com.demo.kafka.springbootkafkaproject.constants.KafkaConsts;
import com.demo.kafka.springbootkafkaproject.constants.LoggerMessages;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private final Logger logger;

    ConsumerService(Logger logger) {
        this.logger = logger;
    }

    @KafkaListener(topics = KafkaConsts.KAFKA_TOPIC, groupId = KafkaConsts.KAFKA_GROUP_ID)
    public void consume(String message) {
        logger.info(String.format(LoggerMessages.CONSUME_MESSAGE, message));
    }
}
