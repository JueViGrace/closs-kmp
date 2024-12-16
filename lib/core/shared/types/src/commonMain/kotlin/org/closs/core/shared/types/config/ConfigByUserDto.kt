package org.closs.core.shared.types.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigByUserDto(
    @SerialName("username")
    val username: String,
)
