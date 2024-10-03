package org.example.controller

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import org.example.client.ProductClient
import org.example.dto.ProductDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-client")
class ProductClientTestController(
        private val productClient: ProductClient
) {
    var log: Logger = LoggerFactory.getLogger(ProductClientTestController::class.java)

    @GetMapping("/test")
    suspend fun test(): ProductDto {
        var product = ProductDto(1, "test", 1.2)
        return product
    }

    @GetMapping("/{id}")
    suspend fun getById(@PathVariable id: Long): ProductDto = coroutineScope {
        val deferred = async(start = CoroutineStart.LAZY) {
            log.info("Pretending doing something useful.")
            delay(3000)
            log.info("Finished doing something useful.")
        }
        val productDtoDeferred: Deferred<ProductDto> = async(start = CoroutineStart.LAZY) {
            log.info("Getting product by id={}.", id)
            productClient.getById(id)
        }

        deferred.start()
        productDtoDeferred.start()
        deferred.await()
        productDtoDeferred.await()
    }

    @GetMapping("/list")
    suspend fun getAll(): Flow<ProductDto> {
        return productClient.findAll()
    }

}