package org.closs.core.shared.types.manager

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerByCode(
    @SerialName("gerencia")
    val manager: String,
    @SerialName("codigo")
    val code: String,
)
