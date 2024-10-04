package org.example.repository

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.example.BaseIT
import org.example.entity.Product
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class ProductRepositoryIT : BaseIT() {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun shouldReturnProductById() = runBlocking {
        //when
        val product = productRepository.findById(1L)
        //then
        assertEquals(Product(1, "test", 1.2), product)
    }

    @Test
    fun shouldReturnByNameContaining() = runBlocking {
        //when
        val productsFlow = productRepository.findByNameContaining("te")
        val products = productsFlow.toList()
        //then
        assertEquals(6, products.size)
    }
}