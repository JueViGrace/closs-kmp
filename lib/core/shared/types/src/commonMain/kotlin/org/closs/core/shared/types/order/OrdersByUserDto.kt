package org.closs.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrdersByUserDto(
    @SerialName("user_code")
    val code: String,
)
