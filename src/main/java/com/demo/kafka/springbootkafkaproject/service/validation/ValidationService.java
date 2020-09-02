package com.demo.kafka.springbootkafkaproject.service.validation;

import com.demo.kafka.springbootkafkaproject.constants.DetailedErrorMessages;
import com.demo.kafka.springbootkafkaproject.error.exceptions.BadRequestException;
import com.demo.kafka.springbootkafkaproject.error.exceptions.InternalServerErrorException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private Logger logger;

    public ValidationService(Logger logger) {
        this.logger = logger;
    }

    public void validateMessage(String message) throws BadRequestException, InternalServerErrorException {
        if (message == null || message.isEmpty()) {
            logger.error(DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK);
            throw new BadRequestException(DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK, null);
        }

        if (message.contains("$")) {
            logger.error(DetailedErrorMessages.CANNOT_PROCESS_MONEY);
            throw new InternalServerErrorException(DetailedErrorMessages.CANNOT_PROCESS_MONEY, null);
        }
    }
}
