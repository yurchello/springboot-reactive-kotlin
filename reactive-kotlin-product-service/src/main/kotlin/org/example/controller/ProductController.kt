package org.example.controller
import kotlinx.coroutines.flow.Flow

import org.example.dto.ProductDto
import org.example.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
        private val productService: ProductService
) {
    @GetMapping("/test")
    suspend fun test(): ProductDto {
        var product = ProductDto(1, "test", 1.2)
        return product
    }

    @GetMapping("/{id}")
    suspend fun getById(@PathVariable id: Long): ProductDto {
        return productService.getById(id)
    }

    @GetMapping("/list")
    suspend fun getAll(): Flow<ProductDto> {
        return productService.getAll()
    }

    @PostMapping
    suspend fun create(@RequestBody productDto: ProductDto): ProductDto {
        return productService.save(productDto)
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: Long, @RequestBody productDto: ProductDto): ProductDto {
        return productService.update(id, productDto)
    }
}