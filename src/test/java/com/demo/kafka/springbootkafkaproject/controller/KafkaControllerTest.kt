package com.demo.kafka.springbootkafkaproject.controller

import com.demo.kafka.springbootkafkaproject.constants.DetailedErrorMessages
import com.demo.kafka.springbootkafkaproject.error.exceptions.BadRequestException
import com.demo.kafka.springbootkafkaproject.service.producer.ProducerService
import com.demo.kafka.springbootkafkaproject.service.validation.ValidationService
import com.nhaarman.mockito_kotlin.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class KafkaControllerTest {

    private var producerService: ProducerService = mock()
    private var validationService: ValidationService = mock()

    @InjectMocks
    private lateinit var kafkaController: KafkaController

    @Test
    fun sendMessageToKafkaTopic_validInput_returns200() {
        val testMessage = "testMessage"

        doNothing().whenever(validationService).validateMessage(testMessage)
        doNothing().whenever(producerService).sendMessage(testMessage)

        kafkaController.postMessageToKafkaTopic(testMessage)

        verify(validationService).validateMessage(testMessage)
        verify(producerService).sendMessage(testMessage)
        verifyNoMoreInteractions(validationService, producerService)
    }

    @Test
    fun sendMessageToKafkaTopic_blankMessage_400BadRequest() {
        val testMessage = ""
        val exception = BadRequestException(DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK, null)

        whenever(validationService.validateMessage(testMessage)).thenThrow(exception)

        val actual = assertThrows<BadRequestException> {
            kafkaController.postMessageToKafkaTopic(testMessage)
        }

        assertEquals(actual.message, exception.message)
        verify(validationService).validateMessage(testMessage)
        verifyZeroInteractions(producerService)
    }
}