package com.demo.kafka.springbootkafkaproject.controller;

import com.demo.kafka.springbootkafkaproject.error.exceptions.BadRequestException;
import com.demo.kafka.springbootkafkaproject.service.producer.ProducerService;
import com.demo.kafka.springbootkafkaproject.service.validation.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final ProducerService producerService;
    private final ValidationService validationService;

    @Autowired
    KafkaController(ProducerService producerService, ValidationService validationService) {
        this.producerService = producerService;
        this.validationService = validationService;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) throws BadRequestException {
        this.validationService.validateMessage(message);
        this.producerService.sendMessage(message);
    }
}
