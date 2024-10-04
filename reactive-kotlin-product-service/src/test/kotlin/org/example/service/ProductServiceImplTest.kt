package org.example.service

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.example.entity.Product
import org.example.mapper.ProductMapper
import org.example.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class ProductServiceImplTest {

    private val productMapper: ProductMapper = Mappers.getMapper(ProductMapper::class.java)
    private val productRepository: ProductRepository = mockk()
    private val productServiceImpl: ProductServiceImpl = ProductServiceImpl(productMapper, productRepository)

    @Test
    fun shouldReturnProductById() = runBlocking {
        //given
        coEvery { productRepository.findById(1L) } returns Product(1, "", 1.1)
        //when
        val productDto = productServiceImpl.getById(1L)
        //then
        assertEquals(1L, productDto.id)
    }

    @Test
    fun shouldReturnAll() = runBlocking {
        //given
        coEvery { productRepository.findAll() } returns flowOf(Product(1, "", 1.1))
        //when
        val flowProduct = productServiceImpl.getAll()
        //then
        val prodList = flowProduct.toList()
        assertEquals(1, prodList.size)
    }
}