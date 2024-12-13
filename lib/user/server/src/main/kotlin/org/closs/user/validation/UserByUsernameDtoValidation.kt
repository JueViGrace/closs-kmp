package org.closs.user.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.user.UserByUsernameDto

fun RequestValidationConfig.validateUserByUsernameDto() {
    validate<UserByUsernameDto> { dto ->
        when {
            dto.username.isEmpty() -> ValidationResult.Invalid("Id must not be empty.")
            else -> ValidationResult.Valid
        }
    }
}
