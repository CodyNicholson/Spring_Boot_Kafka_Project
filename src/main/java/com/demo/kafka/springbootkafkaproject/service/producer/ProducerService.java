package com.demo.kafka.springbootkafkaproject.service.producer;

import com.demo.kafka.springbootkafkaproject.constants.LoggerMessages;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Value("${spring.kafka.topic}")
    private String kafkaUsersTopic;

    private final Logger logger;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ProducerService(Logger logger) {
        this.logger = logger;
    }

    public void sendMessage(String message) {
        logger.info(String.format(LoggerMessages.PRODUCE_MESSAGE, message));
        this.kafkaTemplate.send(kafkaUsersTopic, message);
    }
}
