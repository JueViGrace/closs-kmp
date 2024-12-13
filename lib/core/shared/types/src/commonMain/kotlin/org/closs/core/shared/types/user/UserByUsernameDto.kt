package org.closs.core.shared.types.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserByUsernameDto(
    @SerialName("username")
    val username: String
)
