package org.closs.manager.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.manager.ManagerByCode

fun RequestValidationConfig.validateManagerByCodeDto() {
    validate<ManagerByCode> { dto ->
        when {
            dto.manager.isBlank() -> ValidationResult.Invalid("Código de gerente cannot be empty")
            dto.code.isBlank() -> ValidationResult.Invalid("Código de gerente cannot be empty")
            else -> ValidationResult.Valid
        }
    }
}
