package org.example.repository

import kotlinx.coroutines.flow.Flow
import org.example.entity.Product
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CoroutineCrudRepository<Product, Long> {
    suspend fun findByNameContaining(name: String): Flow<Product>
}