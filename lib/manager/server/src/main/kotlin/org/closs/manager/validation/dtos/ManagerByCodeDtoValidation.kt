package org.closs.manager.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.manager.ManagerByCodeDto

fun RequestValidationConfig.validateManagerByCodeDto() {
    validate<ManagerByCodeDto> { dto ->
        when {
            dto.manager.isBlank() -> ValidationResult.Invalid("Manager cannot be empty")
            dto.code.isBlank() -> ValidationResult.Invalid("Code cannot be empty")
            else -> ValidationResult.Valid
        }
    }
}
