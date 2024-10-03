package org.example.client

import kotlinx.coroutines.flow.Flow
import org.example.dto.ProductDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToFlow

@Service
class ProductClientImpl(
        private val webClient: WebClient
) : ProductClient {

    var log: Logger = LoggerFactory.getLogger(ProductClientImpl::class.java)

    override suspend fun findAll(): Flow<ProductDto> = webClient.get()
            .uri("/product/list")
            .retrieve()
            .bodyToFlow<ProductDto>()

    override suspend fun getById(id: Long): ProductDto = webClient.get()
            .uri("/product/$id")
            .accept(APPLICATION_JSON)
            .retrieve().awaitBody<ProductDto>()

}