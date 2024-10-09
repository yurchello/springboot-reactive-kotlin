package org.example.mapper

import org.example.dto.ProductDto
import org.example.dto.event.ProductSendEvent
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface EventMapper {
    fun toEvent(productDto: ProductDto?): ProductSendEvent
}