package org.closs.core.shared.types.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchBySalesmanCodeDto(
    @SerialName("salesman")
    val code: String,
)
