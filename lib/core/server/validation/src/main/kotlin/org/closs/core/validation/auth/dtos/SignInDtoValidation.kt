package org.closs.core.validation.auth.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.auth.SignInDto

internal fun RequestValidationConfig.validateSignInDto() {
    validate<SignInDto> { dto ->
        when {
            dto.username.isBlank() -> ValidationResult.Invalid("Username cannot be blank")
            dto.password.isBlank() -> ValidationResult.Invalid("Password cannot be blank")
            else -> ValidationResult.Valid
        }
    }
}
