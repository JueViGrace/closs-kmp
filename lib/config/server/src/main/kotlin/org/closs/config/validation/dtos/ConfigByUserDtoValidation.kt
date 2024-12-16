package org.closs.config.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.config.ConfigByUserDto

fun RequestValidationConfig.validateConfigByUserDto() {
    validate<ConfigByUserDto> { dto ->
        when {
            dto.username.isBlank() -> ValidationResult.Invalid("Username is blank")
            else -> ValidationResult.Valid
        }
    }
}
