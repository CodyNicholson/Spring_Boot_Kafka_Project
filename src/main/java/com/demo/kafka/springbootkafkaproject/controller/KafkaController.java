package com.demo.kafka.springbootkafkaproject.controller;

import com.demo.kafka.springbootkafkaproject.error.exceptions.BadRequestException;
import com.demo.kafka.springbootkafkaproject.error.exceptions.InternalServerErrorException;
import com.demo.kafka.springbootkafkaproject.service.producer.ProducerService;
import com.demo.kafka.springbootkafkaproject.service.validation.ValidationService;
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
        this.validationService = validationService;
        this.producerService = producerService;
    }

    @PostMapping(value = "/publish")
    public void postMessageToKafkaTopic(@RequestParam("message") String message) throws BadRequestException, InternalServerErrorException {
        this.validationService.validateMessage(message);
        this.producerService.sendMessage(message);
    }
}
