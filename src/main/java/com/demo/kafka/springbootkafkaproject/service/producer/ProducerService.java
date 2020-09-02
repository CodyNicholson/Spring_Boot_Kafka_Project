package com.demo.kafka.springbootkafkaproject.service.producer;

import com.demo.kafka.springbootkafkaproject.constants.KafkaConsts;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private Logger logger;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ProducerService(Logger logger) {
        this.logger = logger;
    }

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(KafkaConsts.USERS_TOPIC, message);
    }
}
