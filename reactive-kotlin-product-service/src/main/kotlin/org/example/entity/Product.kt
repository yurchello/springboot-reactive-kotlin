package org.example.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("reactive_prod.product")
data class Product(
        @Id val id: Long?,
        var name: String?,
        var price: Double?
)
