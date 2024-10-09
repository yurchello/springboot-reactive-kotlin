package org.example.service

import org.example.dto.event.ProductSendEvent

interface MessagingClient {

    fun sendProduct(productSendEvent: ProductSendEvent)
}