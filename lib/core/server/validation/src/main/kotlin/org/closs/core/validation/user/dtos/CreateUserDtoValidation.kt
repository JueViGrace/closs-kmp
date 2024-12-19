package org.closs.core.validation.user.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.user.CreateUserDto

fun RequestValidationConfig.validateCreateUserDto() {
    validate<CreateUserDto> { dto ->
        when {
            dto.username.isEmpty() -> ValidationResult.Invalid("Username must not be empty.")
            dto.password.isEmpty() -> ValidationResult.Invalid("Password must not be empty.")
            dto.code.isEmpty() -> ValidationResult.Invalid("Code must not be empty.")
            else -> ValidationResult.Valid
        }
    }
}
