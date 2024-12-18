package org.closs.core.shared.types.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchByManagerCodeDto(
    @SerialName("manager")
    val manager: String,
)
