package org.example.service

import kotlinx.coroutines.flow.Flow
import org.example.dto.ProductDto

interface ProductService {
    suspend fun getById(id: Long): ProductDto
    suspend fun getAll(): Flow<ProductDto>
    suspend fun save(productDto: ProductDto): ProductDto
    suspend fun update(id: Long, productDto: ProductDto): ProductDto
}