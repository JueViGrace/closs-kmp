package org.closs.user.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.user.UpdateLastSyncDto
import org.closs.core.util.Util.validUuid

fun RequestValidationConfig.validateUpdateLastSyncDto() {
    validate<UpdateLastSyncDto> { dto ->
        when {
            !validUuid(dto.id) -> ValidationResult.Invalid("Invalid id.")
            dto.lastSync.isEmpty() -> ValidationResult.Invalid("Last sync must not be empty.")
            else -> ValidationResult.Valid
        }
    }
}
