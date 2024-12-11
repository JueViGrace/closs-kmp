package org.closs.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderByIdDto(
    @SerialName("id")
    val id: String,
)
