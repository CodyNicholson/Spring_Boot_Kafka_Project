package com.demo.kafka.springbootkafkaproject.error;

import com.demo.kafka.springbootkafkaproject.constants.RestErrorMessages;
import com.demo.kafka.springbootkafkaproject.error.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(Exception.class);

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<ErrorDetails> handleBadRequestException(Exception ex, WebRequest request) {

        logger.error(RestErrorMessages.BAD_REQEST_MESSAGE, ex);

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                RestErrorMessages.BAD_REQEST_MESSAGE,
                ex.getLocalizedMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { InternalServerErrorException.class, RuntimeException.class })
    protected ResponseEntity<ErrorDetails> handleInternalServerError(Exception ex, WebRequest request) {

        logger.info(RestErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE, ex);

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                RestErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE,
                ex.getLocalizedMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
