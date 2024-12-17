package org.closs.manager.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.manager.CreateManagerDto

fun RequestValidationConfig.validateCreateManagerDto() {
    validate<CreateManagerDto> { dto ->
        when {
            dto.kngCodgcia.isBlank() -> ValidationResult.Invalid("Código de gerencia cannot be empty")
            dto.kngCodcoord.isBlank() -> ValidationResult.Invalid("Código de coordinador cannot be empty")
            else -> ValidationResult.Valid
        }
    }
}