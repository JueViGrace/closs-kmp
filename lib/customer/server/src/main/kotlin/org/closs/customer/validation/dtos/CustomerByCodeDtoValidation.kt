package org.closs.customer.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.customer.CustomerByCodeDto

fun RequestValidationConfig.validateCustomerByCodeDto() {
    validate<CustomerByCodeDto> { dto ->
        when {
            dto.code.isBlank() -> ValidationResult.Invalid("Codigo cannot be empty")
            else -> ValidationResult.Valid
        }
    }
}
