package com.demo.kafka.springbootkafkaproject.service.validation;

import com.demo.kafka.springbootkafkaproject.constants.DetailedErrorMessages;
import com.demo.kafka.springbootkafkaproject.error.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public void validateMessage(String message) throws BadRequestException {
        if (message.isEmpty()) {
            throw new BadRequestException(DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK, null);
        }
    }
}
