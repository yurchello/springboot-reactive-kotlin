package org.example.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.dto.ProductDto
import org.example.mapper.ProductMapper
import org.example.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductServiceImpl(
        private val productMapper: ProductMapper,
        private val productRepository: ProductRepository) : ProductService {

    override suspend fun getById(id: Long): ProductDto {
        val product = productRepository.findById(id)
        return productMapper.toDto(product)
    }

    override suspend fun getAll(): Flow<ProductDto> {
        return productRepository.findAll().map { productMapper.toDto(it) }
    }

    override suspend fun save(productDto: ProductDto): ProductDto {
        val product = productMapper.toEntity(productDto.copy(id = null))
        return productMapper.toDto(productRepository.save(product))
    }

    override suspend fun update(id: Long, productDto: ProductDto): ProductDto {
        val product = productRepository.findById(id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id $id was not found.")
        val productFromDto = productMapper.toEntity(productDto)

        val productSaved = productRepository.save(
                productFromDto.copy(id = product.id)
        )
        return productMapper.toDto(productSaved)
    }
}