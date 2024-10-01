package org.example.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.dto.ProductDto
import org.example.entity.Product
import org.example.mapper.ProductMapper
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl : ProductService{
    override suspend fun getById(id: Long): ProductDto {

        return ProductDto(1,"test", 3.4)
    }

    override suspend fun getAll(): Flow<ProductDto> {
        return flow {
            repeat(6) { it ->
                emit(ProductDto(1,"test", 3.4))
                delay(3000)
            }
        }
    }

    override suspend fun save(productDto: ProductDto): ProductDto {
        return ProductDto(1,"test", 3.4)
    }

    override suspend fun update(id: Long, productDto: ProductDto): ProductDto {
        return ProductDto(1,"test", 3.4)
    }
}