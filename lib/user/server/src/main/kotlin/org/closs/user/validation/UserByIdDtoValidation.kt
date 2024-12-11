package org.closs.user.validation

import org.closs.core.util.Util.validUuid
import org.closs.core.shared.types.user.UserByIdDto
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateUserByIdDto() {
    validate<UserByIdDto> { dto ->
        when {
            dto.id.isEmpty() -> ValidationResult.Invalid("Id must not be empty.")
            !validUuid(dto.id) -> ValidationResult.Invalid("Invalid id.")
            else -> ValidationResult.Valid
        }
    }
}
