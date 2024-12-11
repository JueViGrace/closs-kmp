package org.closs.order.validation

import org.closs.core.shared.types.order.OrdersByUserDto
import org.closs.core.util.Util.validUuid
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateOrderByUser() {
    validate<OrdersByUserDto> { dto ->
        when {
            dto.userId.isBlank() -> ValidationResult.Invalid("Id cannot be blank")
            !validUuid(dto.userId) -> ValidationResult.Invalid("Id is not valid")
            else -> ValidationResult.Valid
        }
    }
}
