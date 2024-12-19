package org.closs.core.shared.types.manager

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerByCodeDto(
    @SerialName("manager")
    val manager: String,
    @SerialName("code")
    val code: String,
)
