package org.closs.core.shared.types.manager

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagersByCode(
    @SerialName("codigo")
    val code: String
)
