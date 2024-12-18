package org.closs.order.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateOrderByUser() {
    validate<OrdersBySalesmanDto> { dto ->
        when {
            dto.code.isBlank() -> ValidationResult.Invalid("Code cannot be blank")
            else -> ValidationResult.Valid
        }
    }
}
