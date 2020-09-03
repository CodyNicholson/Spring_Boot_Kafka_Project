package com.demo.kafka.springbootkafkaproject.constants

object RestErrorMessages {
    const val BAD_REQEST_MESSAGE = "400: Bad Request"
    const val FORBIDDEN_MESSAGE = "403: Forbidden"
    const val NOT_FOUND_MESSAGE = "404: Not Found"
    const val CONFLICT_MESSAGE = "409: Conflict"
    const val UNSUPPORTED_MEDIA_TYPE_MESSAGE = "415: Unsupported Media Type"
    const val INTERNAL_SERVER_ERROR_MESSAGE = "500: Internal Server Error"
    const val SERVICE_UNAVAILABLE_MESSAGE = "503: Service Unavailable"
}

object DetailedErrorMessages {
    const val MESSAGE_CANNOT_BE_BLANK = "Message cannot be blank."
    const val CANNOT_PROCESS_MONEY = "We cannot process money."
}

object LoggerMessages {
    const val CONSUME_MESSAGE = "#### -> Consumed message -> %s"
    const val PRODUCE_MESSAGE = "#### -> Producing message -> %s"
}

object KafkaConsts {
    const val KAFKA_TOPIC = "#{'\${spring.kafka.topic}'}"
    const val KAFKA_GROUP_ID = "#{'\${spring.kafka.consumer.group-id}'}"
}