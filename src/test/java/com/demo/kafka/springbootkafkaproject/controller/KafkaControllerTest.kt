package com.demo.kafka.springbootkafkaproject.controller

import com.demo.kafka.springbootkafkaproject.constants.DetailedErrorMessages
import com.demo.kafka.springbootkafkaproject.error.exceptions.BadRequestException
import com.demo.kafka.springbootkafkaproject.service.producer.ProducerService
import com.demo.kafka.springbootkafkaproject.service.validation.ValidationService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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

        kafkaController.sendMessageToKafkaTopic(testMessage)

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
            kafkaController.sendMessageToKafkaTopic(testMessage)
        }

        assertEquals(actual.message, exception.message)
        verify(validationService).validateMessage(testMessage)
        verifyZeroInteractions(producerService)
    }
}