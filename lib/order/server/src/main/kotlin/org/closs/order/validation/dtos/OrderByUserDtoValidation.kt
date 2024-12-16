package org.closs.order.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.order.OrdersByUserDto

fun RequestValidationConfig.validateOrderByUser() {
    validate<OrdersByUserDto> { dto ->
        when {
            dto.code.isBlank() -> ValidationResult.Invalid("Code cannot be blank")
            else -> ValidationResult.Valid
        }
    }
}
