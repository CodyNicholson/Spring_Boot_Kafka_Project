package com.demo.kafka.springbootkafkaproject.integration

import com.demo.kafka.springbootkafkaproject.constants.DetailedErrorMessages
import com.demo.kafka.springbootkafkaproject.constants.RestErrorMessages
import com.demo.kafka.springbootkafkaproject.error.ErrorDetails
import com.demo.kafka.springbootkafkaproject.util.SpringCommandLineProfileResolver
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = ["DOCKER_TOOLBOX"], resolver = SpringCommandLineProfileResolver::class)
class RestIntegrationTests {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    private val objectMapper = ObjectMapper()

    @Test
    fun postMessageToKafkaTopic_validMessage_200Success() {
        val testMessage = "testMessage"

        val result = testRestTemplate.postForEntity("/kafka/publish?message=$testMessage", null, String::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertNull(result.body)
    }

    @Test
    fun postMessageToKafkaTopic_emptyMessage_400BadRequest() {
        val testMessage = ""

        val result = testRestTemplate.postForEntity("/kafka/publish?message=$testMessage", null, String::class.java)

        val actual = objectMapper.readValue(result.body, ErrorDetails::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertNotNull(actual.time)
        assertEquals(actual.restErrorMessage, RestErrorMessages.BAD_REQEST_MESSAGE)
        assertEquals(actual.detailedMessage, DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK)
    }

    @Test
    fun postMessageToKafkaTopic_nullMessage_400BadRequest() {
        val result = testRestTemplate.postForEntity("/kafka/publish?message=", null, String::class.java)

        val actual = objectMapper.readValue(result.body, ErrorDetails::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertNotNull(actual.time)
        assertEquals(actual.restErrorMessage, RestErrorMessages.BAD_REQEST_MESSAGE)
        assertEquals(actual.detailedMessage, DetailedErrorMessages.MESSAGE_CANNOT_BE_BLANK)
    }

    @Test
    fun postMessageToKafkaTopic_messageWithMoney_500InternalServerError() {
        val testMessage = "moneyMessage$$$"

        val result = testRestTemplate.postForEntity("/kafka/publish?message=$testMessage", null, String::class.java)

        val actual = objectMapper.readValue(result.body, ErrorDetails::class.java)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.statusCode)
        assertNotNull(actual.time)
        assertEquals(actual.restErrorMessage, RestErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE)
        assertEquals(actual.detailedMessage, DetailedErrorMessages.CANNOT_PROCESS_MONEY)
    }
}