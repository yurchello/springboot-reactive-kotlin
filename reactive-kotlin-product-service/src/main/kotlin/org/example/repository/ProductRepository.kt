package org.example.repository

import kotlinx.coroutines.flow.Flow
import org.example.entity.Product
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProductRepository : CoroutineCrudRepository<Product, Long> {
    fun findByNameContaining(name: String): Flow<Product>
}