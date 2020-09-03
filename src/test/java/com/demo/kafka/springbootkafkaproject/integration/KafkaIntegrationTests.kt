package com.demo.kafka.springbootkafkaproject.integration

import com.demo.kafka.springbootkafkaproject.util.SpringCommandLineProfileResolver
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.ActiveProfiles
import java.time.Duration
import java.util.*

@SpringBootTest
@ActiveProfiles(value = ["DOCKER_TOOLBOX"], resolver = SpringCommandLineProfileResolver::class)
class KafkaIntegrationTests {
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var kafkaTemplate1: KafkaTemplate<String, String>

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var kafkaTemplate2: KafkaTemplate<String, String>

    @Value("\${spring.kafka.topic}")
    private lateinit var kafkaUsersTopic: String
    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var kafkaBootstrapServers: String
    @Value("\${spring.kafka.consumer.key-deserializer}")
    private lateinit var kafkaKeyDeserializer: String
    @Value("\${spring.kafka.consumer.value-deserializer}")
    private lateinit var kafkaValueDeserializer: String
    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var kafkaGroupId: String
    private val kafkaProperties = Properties()

    private val objectMapper = ObjectMapper()
    private val testMessageValid = "testMessageValid"

    @BeforeEach
    fun setUp() {
        kafkaProperties["bootstrap.servers"] = kafkaBootstrapServers
        kafkaProperties["group.id"] = kafkaGroupId
        kafkaProperties["key.deserializer"] = kafkaKeyDeserializer
        kafkaProperties["value.deserializer"] = kafkaValueDeserializer
        kafkaProperties["auto.offset.reset"] = "earliest"
        publishMessage1(testMessageValid)
        publishMessage1(testMessageValid)
        publishMessage1(testMessageValid)
        publishMessage2(testMessageValid)
        publishMessage2(testMessageValid)
        publishMessage2(testMessageValid)
    }

    @Test
    fun `Receive message, org ID and user ID exists in SRS, publishes calculated reward`() {
        val actual = consumeMessage(testMessageValid)
        val expected = testMessageValid

        // assert
        assertEquals(expected, actual)
    }

    private fun publishMessage1(message: String) {
        kafkaTemplate1.send(kafkaUsersTopic, message).get()
    }

    private fun publishMessage2(message: String) {
        kafkaTemplate2.send(kafkaUsersTopic, message).get()
    }

    private fun consumeMessage(message: String): String? {
        val consumer = KafkaConsumer<String, String>(kafkaProperties)
        consumer.subscribe(listOf(kafkaUsersTopic))

        val l = consumer.position(consumer.assignment().first())

        val records = consumer.poll(Duration.ofMillis(10000))
        consumer.close()

        return if (records.isEmpty)
            null
        else objectMapper.readValue(records.filter { it.value() == message }[0].value(), String::class.java)
    }

    private fun consumeLastMessage(): String? {
        val consumer = KafkaConsumer<String, String>(kafkaProperties)
        consumer.subscribe(listOf(kafkaUsersTopic))

        val records = consumer.poll(Duration.ofMillis(10000))
        consumer.close()

        return if (records.isEmpty)
            null
        else
            records.last().value()
    }
}