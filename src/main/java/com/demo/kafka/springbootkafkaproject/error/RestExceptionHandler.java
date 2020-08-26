package com.demo.kafka.springbootkafkaproject.error;

import com.demo.kafka.springbootkafkaproject.constants.RestExceptionConstants;
import com.demo.kafka.springbootkafkaproject.error.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(Exception.class);

    @ExceptionHandler(value = { BadRequestException.class })
    protected BadRequestException handleBadRequest(Exception ex, WebRequest request) {

        logger.debug(ex.getMessage(), ex);

        return new BadRequestException(RestExceptionConstants.BAD_REQEST_MESSAGE, ex);
    }
}
