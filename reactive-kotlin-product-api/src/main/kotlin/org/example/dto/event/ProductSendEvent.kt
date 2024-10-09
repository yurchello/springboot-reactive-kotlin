package org.example.dto.event

data class ProductSendEvent(var id: Long?,
                            var name: String?,
                            var price: Double?,
                            var barCode: String?) {
}