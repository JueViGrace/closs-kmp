package org.closs.core.shared.types.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerByCodeDto(
    @SerialName("codigo")
    val code: String,
)
