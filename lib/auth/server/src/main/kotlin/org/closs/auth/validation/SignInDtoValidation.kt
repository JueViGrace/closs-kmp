package org.closs.auth.validation

import org.closs.core.shared.types.auth.SignInDto
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

internal fun RequestValidationConfig.validateSignInDto() {
    validate<SignInDto> { dto ->
        when {
            dto.username.isBlank() -> ValidationResult.Invalid("Username cannot be blank")
            dto.password.isBlank() -> ValidationResult.Invalid("Password cannot be blank")
            else -> ValidationResult.Valid
        }
    }
}
