package com.demo.kafka.springbootkafkaproject.service.producer

import com.demo.kafka.springbootkafkaproject.constants.KafkaConsts
import com.nhaarman.mockito_kotlin.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.util.ReflectionTestUtils

@ExtendWith(MockitoExtension::class)
class ProducerServiceTest {

    private var logger: Logger = mock()

    private var kafkaTemplate: KafkaTemplate<String, String> = mock()

    @InjectMocks
    private lateinit var producerService: ProducerService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        ReflectionTestUtils.setField(producerService, "kafkaUsersTopic", KafkaConsts.KAFKA_TOPIC)
    }

    @Test
    fun sendMessage_validMessage_logsAndSucceeds() {
        // assemble
        val inputMessage = "testMessage"
        val testMessage = "#### -> Producing message -> $inputMessage"

        doNothing().whenever(logger).info(testMessage)

        // act
        producerService.sendMessage(inputMessage)

        // assert
        verify(logger).info(testMessage)
        verify(kafkaTemplate).send(KafkaConsts.KAFKA_TOPIC, inputMessage)
        verifyNoMoreInteractions(logger, kafkaTemplate)
    }

    @Test
    fun sendMessage_validButEmptyMessage_logsAndSucceeds() {
        // assemble
        val inputMessage = ""
        val testMessage = "#### -> Producing message -> $inputMessage"

        doNothing().whenever(logger).info(testMessage)

        // act
        producerService.sendMessage(inputMessage)

        // assert
        verify(logger).info(testMessage)
        verify(kafkaTemplate).send(KafkaConsts.KAFKA_TOPIC, inputMessage)
        verifyNoMoreInteractions(logger)
    }

    @Test
    fun sendMessage_validNullMessage_logsAndSucceeds() {
        // assemble
        val inputMessage = null
        val testMessage = "#### -> Producing message -> $inputMessage"

        doNothing().whenever(logger).info(testMessage)

        // act
        producerService.sendMessage(inputMessage)

        // assert
        verify(logger).info(testMessage)
        verify(kafkaTemplate).send(KafkaConsts.KAFKA_TOPIC, inputMessage)
        verifyNoMoreInteractions(logger)
    }
}