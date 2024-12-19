package org.closs.order.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.order.OrderByIdDto

fun RequestValidationConfig.validateOrderById() {
    validate<OrderByIdDto> { dto ->
        when {
            dto.ktiNdoc.isBlank() -> ValidationResult.Invalid("Order id cannot be blank")
            else -> ValidationResult.Valid
        }
    }
}
