package org.closs.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDetailsDto(
    @SerialName("order_id")
    val orderId: String,
    @SerialName("product_id")
    val productId: String,
    @SerialName("images")
    val images: List<String>,
)
