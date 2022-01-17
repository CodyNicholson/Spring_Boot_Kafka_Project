package com.demo.kafka.springbootkafkaproject.controller;

import com.demo.kafka.springbootkafkaproject.dto.MessageDto;
import com.demo.kafka.springbootkafkaproject.dto.MessagesDto;
import com.demo.kafka.springbootkafkaproject.error.exceptions.BadRequestException;
import com.demo.kafka.springbootkafkaproject.error.exceptions.InternalServerErrorException;
import com.demo.kafka.springbootkafkaproject.service.producer.ProducerService;
import com.demo.kafka.springbootkafkaproject.service.validation.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PostMapping(value = "/msg")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity postMessageToKafkaTopic(@RequestBody MessageDto message) throws BadRequestException, InternalServerErrorException {
        this.producerService.sendMessage(message.getMessage());
        return new ResponseEntity(HttpStatus.OK);
    }

    // Takes 100 messages of 10,000 characters each
    @PostMapping(value = "/msgs")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity postMessagesToKafkaTopic() throws BadRequestException, InternalServerErrorException {
        this.producerService.sendMessages();

        return new ResponseEntity(HttpStatus.OK);
    }

    // Takes one 1,000,000 character message
    @PostMapping(value = "/bigpost")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity bigPostMessage(@RequestBody MessageDto message) throws BadRequestException, InternalServerErrorException {
        Date now = new Date();
        System.out.println("BIG POST RECEIVED TIMESTAMP: " + getSecondsStringFromDate(now));
        System.out.println(message);

        return new ResponseEntity(HttpStatus.OK);
    }

    private String getSecondsStringFromDate(Date date) {
        return  date.toInstant().toString().substring(14, 23);
    }
}
