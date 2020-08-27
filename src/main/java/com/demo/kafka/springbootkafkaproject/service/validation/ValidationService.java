package com.demo.kafka.springbootkafkaproject.service.validation;

import com.demo.kafka.springbootkafkaproject.constants.DetailedErrorMessages;
import com.demo.kafka.springbootkafkaproject.error.exceptions.BadRequestException;
import com.demo.kafka.springbootkafkaproject.error.exceptions.InternalServerErrorException;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public void validateMessage(String message) throws BadRequestException, InternalServerErrorException {
        if (message.isEmpty()) {
            throw new BadRequestException(DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK, null);
        }

        if (message.contains("$")) {
            throw new InternalServerErrorException(DetailedErrorMessages.CANNOT_PROCESS_MONEY, null);
        }
    }
}
