package org.closs.core.shared.types.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id")
    val id: String,
    @SerialName("images")
    val images: List<String>,
)
