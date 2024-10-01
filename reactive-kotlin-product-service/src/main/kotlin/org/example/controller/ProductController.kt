package org.example.controller

import org.example.dto.ProductDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController {
    @GetMapping("/test")
    fun test(): ProductDto {
        var product = ProductDto(1, "test", 1.2)
        return product
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ProductDto {
        return ProductDto(1, "test", 1.2)
    }

    @GetMapping("/list")
    fun getAll(): List<ProductDto>{
        var product = ProductDto(1, "test", 1.2)
        return listOf(product)
    }

    @PostMapping
    fun create(@RequestBody productDto: ProductDto): ProductDto {
        return ProductDto(1, "test", 1.2)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody productDto: ProductDto): ProductDto {
        return ProductDto(1, "test", 1.2)
    }
}