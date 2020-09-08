package com.demo.kafka.springbootkafkaproject.integration.models

import java.text.SimpleDateFormat
import java.util.*

data class KafkaRecord(val topic: String,
                       val partition: Int,
                       val offset: Long,
                       val timestamp: Long,
                       val key: String?,
                       val value: String?) {
    override fun toString(): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")

        return "Topic: $topic, Partition: $partition, Offset: $offset, Timestamp: ${format.format(date)}, Key: $key, Value: $value"
    }
}