package org.example.service

import org.example.dto.event.ProductSendEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaMessagingClient (
        @Value("\${topic.send-product}")
        private val sendClientTopic: String,
        private val kafkaTemplate: KafkaTemplate<String?, Any?>) : MessagingClient {
    override fun sendProduct(productSendEvent: ProductSendEvent) {
        kafkaTemplate.send(sendClientTopic, productSendEvent.barCode!!, productSendEvent)
    }
}