package com.demo.kafka.springbootkafkaproject.service.consumer

import com.nhaarman.mockito_kotlin.*
import org.mockito.Mock
import org.mockito.InjectMocks
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger

@ExtendWith(MockitoExtension::class)
internal class ConsumerServiceTest {

    @Mock
    private lateinit var logger: Logger

    @InjectMocks
    private lateinit var consumerService: ConsumerService

    @Test
    fun consume_validMessage_logsAndSucceeds() {
        // assemble
        val inputMessage = "testMessage"
        val testMessage = "#### -> Consumed message -> $inputMessage"

        doNothing().whenever(logger).info(testMessage)

        // act
        consumerService.consume(inputMessage)

        // assert
        verify(logger).info(testMessage)
        verifyNoMoreInteractions(logger)
    }

    @Test
    fun consume_validButEmptyMessage_logsAndSucceeds() {
        // assemble
        val inputMessage = ""
        val testMessage = "#### -> Consumed message -> $inputMessage"

        doNothing().whenever(logger).info(testMessage)

        // act
        consumerService.consume(inputMessage)

        // assert
        verify(logger).info(testMessage)
        verifyNoMoreInteractions(logger)
    }

    @Test
    fun consume_validNullMessage_logsAndSucceeds() {
        // assemble
        val inputMessage = null
        val testMessage = "#### -> Consumed message -> $inputMessage"

        doNothing().whenever(logger).info(testMessage)

        // act
        consumerService.consume(inputMessage)

        // assert
        verify(logger).info(testMessage)
        verifyNoMoreInteractions(logger)
    }
}
