package org.example.mapper

import org.example.dto.ProductDto
import org.example.entity.Product
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ProductMapper {
    fun toEntity(productDto: ProductDto): Product
    fun toDto(product: Product): ProductDto
}