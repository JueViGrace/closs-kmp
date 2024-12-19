package org.closs.document.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.document.DocumentByCodeDto

fun RequestValidationConfig.validateDocumentByCodeDto() {
    validate<DocumentByCodeDto> { dto ->
        when {
            dto.document.isBlank() -> ValidationResult.Invalid("Documento cannot be empty")
            else -> ValidationResult.Valid
        }
    }
}
