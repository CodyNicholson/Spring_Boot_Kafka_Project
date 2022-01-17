package com.demo.kafka.springbootkafkaproject.service.consumer;

import com.demo.kafka.springbootkafkaproject.constants.KafkaConsts;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConsumerService {

    private final Logger logger;

    ConsumerService(Logger logger) {
        this.logger = logger;
    }

    @KafkaListener(topics = KafkaConsts.KAFKA_TOPIC, groupId = KafkaConsts.KAFKA_GROUP_ID)
    public String consume(String message) {
        logger.info(String.format(logConsumeMessage(), message));
        return message;
    }

    private String logConsumeMessage() {
        Date now = new Date();
        return "\nTimestamp Consumed: " + getSecondsStringFromDate(now) + "\nConsumed message -> %s";
    }

    private String getSecondsStringFromDate(Date date) {
        return  date.toInstant().toString().substring(14, 23);
    }
}