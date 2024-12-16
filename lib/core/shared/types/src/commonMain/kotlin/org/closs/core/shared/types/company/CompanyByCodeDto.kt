package org.closs.core.shared.types.company

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyByCodeDto(
    @SerialName("codigo")
    val code: String
)
