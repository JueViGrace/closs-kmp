package org.closs.document.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.document.DocumentByCode
import sun.security.util.KeyUtil.validate

fun RequestValidationConfig.validateDocumentByCodeDto() {
    validate<DocumentByCode> { dto ->
        when {
            dto.document.isBlank() -> ValidationResult.Invalid("Documento cannot be empty")
            else -> ValidationResult.Valid
        }
    }
}
