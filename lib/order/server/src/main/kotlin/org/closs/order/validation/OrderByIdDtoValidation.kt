package org.closs.order.validation

import org.closs.core.shared.types.order.OrderByIdDto
import org.closs.core.util.Util.validUuid
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateOrderById() {
    validate<OrderByIdDto> { dto ->
        when {
            dto.id.isBlank() -> ValidationResult.Invalid("Id cannot be blank")
            !validUuid(dto.id) -> ValidationResult.Invalid("Id is not valid")
            else -> ValidationResult.Valid
        }
    }
}
