package org.closs.manager.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.manager.ManagersByCode

fun RequestValidationConfig.validateManagersByCodeDto() {
    validate<ManagersByCode> { dto ->
        when {
            dto.code.isBlank() -> ValidationResult.Invalid("Código de gerente cannot be empty")
            else -> ValidationResult.Valid
        }
    }
}
