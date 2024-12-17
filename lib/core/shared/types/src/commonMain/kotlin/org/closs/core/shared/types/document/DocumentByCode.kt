package org.closs.core.shared.types.document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DocumentByCode(
    @SerialName("documento")
    val document: String
)
