package org.example.service

import org.example.mapper.EventMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MessageSyncServiceImpl(
        private val messagingClient: MessagingClient,
        private val eventMapper: EventMapper,
        private val productService: ProductService
) : MessageSyncService {

    var log: Logger = LoggerFactory.getLogger(MessageSyncServiceImpl::class.java)

    override suspend fun sendMessage(id: Long) {
        log.info("Sending message. id={}", id)
        val productDto = productService.getById(id)
        val event = eventMapper.toEvent(productDto)
        event.barCode = "1"
        messagingClient.sendProduct(event)
        log.info("The message was sent. event={}", event)
    }
}