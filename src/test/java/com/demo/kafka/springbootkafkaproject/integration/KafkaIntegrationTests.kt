package com.demo.kafka.springbootkafkaproject.integration

import com.demo.kafka.springbootkafkaproject.integration.models.KafkaRecord
import com.demo.kafka.springbootkafkaproject.util.SpringCommandLineProfileResolver
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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

    private val kafkaProperties1 = Properties()
    private val kafkaProperties2 = Properties()

    private val testMessageValid = "testMessageValidX"

    @BeforeEach
    fun setUp() {
        kafkaProperties1["bootstrap.servers"] = kafkaBootstrapServers; kafkaProperties2["bootstrap.servers"] = kafkaBootstrapServers
        kafkaProperties1["key.deserializer"] = kafkaKeyDeserializer; kafkaProperties2["key.deserializer"] = kafkaKeyDeserializer
        kafkaProperties1["value.deserializer"] = kafkaValueDeserializer; kafkaProperties2["value.deserializer"] = kafkaValueDeserializer
        kafkaProperties1["auto.offset.reset"] = "earliest"; kafkaProperties2["auto.offset.reset"] = "earliest"
        kafkaProperties1["group.id"] = UUID.randomUUID().toString(); kafkaProperties2["group.id"] = UUID.randomUUID().toString()

        publishMessage1(testMessageValid)
        publishMessage2(testMessageValid)
    }

    @Test
    fun `consume kafka messages, valid messages published to topic, messages consumed and output correctly`() {
        val actualLastMessage = consumeLastMessage()
        val actualMessages = consumeMessages()
        val expectedLastMessage = KafkaRecord(topic = kafkaUsersTopic, partition = 0, offset = 0, timestamp = 0, key = null, value = testMessageValid)

        // assert
        assertTrue(actualMessages.size > 1)
        assertEquals(expectedLastMessage.topic, actualLastMessage?.topic)
        assertEquals(expectedLastMessage.partition, actualLastMessage?.partition)
        assertEquals(expectedLastMessage.value, actualLastMessage?.value)
        assertTrue(actualLastMessage?.offset!! > 0)
    }

    private fun publishMessage1(message: String) {
        kafkaTemplate1.send(kafkaUsersTopic, message).get()
    }

    private fun publishMessage2(message: String) {
        kafkaTemplate2.send(kafkaUsersTopic, message).get()
    }

    private fun consumeMessages(): MutableList<KafkaRecord> {
        val consumer = KafkaConsumer<String, String>(kafkaProperties1)
        consumer.subscribe(listOf(kafkaUsersTopic))

        val records = consumer.poll(Duration.ofMillis(10000))
        consumer.close()

        val recordDtos = mutableListOf<KafkaRecord>()

        for (record in records) {
            val kafkaRecord = KafkaRecord(record.topic(), record.partition(), record.offset(), record.timestamp(), record.key(), record.value())
            kafkaRecord.toString()
            recordDtos.add(kafkaRecord)
        }

        return recordDtos
    }

    private fun consumeLastMessage(): KafkaRecord? {
        val consumer = KafkaConsumer<String, String>(kafkaProperties2)
        consumer.subscribe(listOf(kafkaUsersTopic))

        val records = consumer.poll(Duration.ofMillis(10000))
        val lastRecord = records.last()
        consumer.close()

        val lastKafkaRecord = KafkaRecord(lastRecord.topic(), lastRecord.partition(), lastRecord.offset(), lastRecord.timestamp(), lastRecord.key(), lastRecord.value())
        lastKafkaRecord.toString()

        return lastKafkaRecord
    }
}
