package com.demo.kafka.springbootkafkaproject.service.validation

import com.demo.kafka.springbootkafkaproject.constants.DetailedErrorMessages
import com.demo.kafka.springbootkafkaproject.error.exceptions.BadRequestException
import com.demo.kafka.springbootkafkaproject.error.exceptions.InternalServerErrorException
import com.nhaarman.mockito_kotlin.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger

@ExtendWith(MockitoExtension::class)
internal class ValidationServiceTest {

    @Mock
    private lateinit var logger: Logger

    @InjectMocks
    private lateinit var validationService: ValidationService

    @Test
    fun validateMessage_validMessage_succeeds() {
        // assemble
        val inputMessage = "testMessage"

        // act
        validationService.validateMessage(inputMessage)

        // assert
        verifyZeroInteractions(logger)
    }

    @Test
    fun validateMessage_blankMessage_400BadRequest() {
        // assemble
        val inputMessage = ""

        doNothing().whenever(logger).error(DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK)

        // act
        val actual = assertThrows<BadRequestException> {
            validationService.validateMessage(inputMessage)
        }

        // assert
        assertEquals(actual.message, DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK)
        verify(logger).error(DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK)
        verifyZeroInteractions(logger)
    }

    @Test
    fun validateMessage_nullMessage_400BadRequest() {
        // assemble
        val inputMessage = null

        doNothing().whenever(logger).error(DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK)

        // act
        val actual = assertThrows<BadRequestException> {
            validationService.validateMessage(inputMessage)
        }

        // assert
        assertEquals(actual.message, DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK)
        verify(logger).error(DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK)
        verifyZeroInteractions(logger)
    }

    @Test
    fun validateMessage_messageWithMoney_400BadRequest() {
        // assemble
        val inputMessage = "null$"

        doNothing().whenever(logger).error(DetailedErrorMessages.CANNOT_PROCESS_MONEY)

        // act
        val actual = assertThrows<InternalServerErrorException> {
            validationService.validateMessage(inputMessage)
        }

        // assert
        assertEquals(actual.message, DetailedErrorMessages.CANNOT_PROCESS_MONEY)
        verify(logger).error(DetailedErrorMessages.CANNOT_PROCESS_MONEY)
        verifyZeroInteractions(logger)
    }
}