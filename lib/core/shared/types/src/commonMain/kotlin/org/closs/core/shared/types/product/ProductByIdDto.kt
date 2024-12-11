package org.closs.core.shared.types.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductByIdDto(
    @SerialName("id")
    val id: String
)
