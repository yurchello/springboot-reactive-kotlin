package org.example.client

import kotlinx.coroutines.flow.Flow
import org.example.dto.ProductDto

interface ProductClient {
    suspend fun getById(id: Long): ProductDto

    suspend fun findAll(): Flow<ProductDto>
}