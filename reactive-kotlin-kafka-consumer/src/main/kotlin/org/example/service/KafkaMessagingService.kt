package org.example.service

import org.example.dto.event.ProductSendEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class KafkaMessagingService {

    var log: Logger = LoggerFactory.getLogger(KafkaMessagingService::class.java)

    companion object {
        private const val TOPIC_CREATE_ORDER = "\${topic.send-product}"
        private const val KAFKA_CONSUMER_GROUP_ID = "\${spring.kafka.consumer.group-id}"
    }

    @KafkaListener(
            topics = [TOPIC_CREATE_ORDER],
            groupId = KAFKA_CONSUMER_GROUP_ID,
            properties = ["spring.json.value.default.type=org.example.dto.event.ProductSendEvent"])
    fun printOrder(orderEvent: ProductSendEvent?, @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
                   @Header(KafkaHeaders.OFFSET) offset: Long): ProductSendEvent? {
        log.info("Message consumed (CLI consumer) {}", orderEvent)
        return orderEvent
    }
}