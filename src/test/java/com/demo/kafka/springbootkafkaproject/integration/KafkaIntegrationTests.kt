package com.demo.kafka.springbootkafkaproject.integration

import com.demo.kafka.springbootkafkaproject.integration.models.KafkaRecord
import com.demo.kafka.springbootkafkaproject.util.SpringCommandLineProfileResolver
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.junit.jupiter.api.Assertions.*
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

    private val kafkaProperties = Properties()

    private val testMessageValid = "testMessageValidX"

    @BeforeEach
    fun setUp() {
        kafkaProperties["bootstrap.servers"] = kafkaBootstrapServers
        kafkaProperties["key.deserializer"] = kafkaKeyDeserializer
        kafkaProperties["value.deserializer"] = kafkaValueDeserializer
        kafkaProperties["auto.offset.reset"] = "earliest"
        kafkaProperties["group.id"] = UUID.randomUUID().toString()

        publishMessage1(testMessageValid)
        publishMessage2(testMessageValid)
    }

    @Test
    fun `consume all kafka messages, valid messages published to topic, messages consumed and output correctly`() {
        val firstOffset = 1
        val secondOffset = 2
        val partition = 0

        val actualMessages = consumeMessages()

        assertTrue(actualMessages.size > 1)
        assertNotNull(actualMessages[0].offset.toInt() == firstOffset)
        assertNotNull(actualMessages[0].partition == partition)
        assertNotNull(actualMessages[0].timestamp)
        assertNotNull(actualMessages[1].offset.toInt() == secondOffset)
        assertNotNull(actualMessages[1].partition == partition)
        assertNotNull(actualMessages[1].timestamp)
    }

    @Test
    fun `consume last kafka messages, valid messages published to topic, messages consumed and output correctly`() {
        val expectedLastMessage = KafkaRecord(topic = kafkaUsersTopic, partition = 0, offset = 0, timestamp = 0, key = null, value = testMessageValid)

        val actualLastMessage = consumeLastMessage()

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
        val consumer = KafkaConsumer<String, String>(kafkaProperties)
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
        val consumer = KafkaConsumer<String, String>(kafkaProperties)
        consumer.subscribe(listOf(kafkaUsersTopic))

        val records = consumer.poll(Duration.ofMillis(10000))
        val lastRecord = records.last()
        consumer.close()

        val lastKafkaRecord = KafkaRecord(lastRecord.topic(), lastRecord.partition(), lastRecord.offset(), lastRecord.timestamp(), lastRecord.key(), lastRecord.value())
        lastKafkaRecord.toString()

        return lastKafkaRecord
    }
}
