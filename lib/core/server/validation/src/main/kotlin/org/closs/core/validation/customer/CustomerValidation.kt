package org.closs.core.validation.customer

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.customer.dtos.validateCreateCustomerDto
import org.closs.core.validation.customer.dtos.validateUpdateCustomerDto

fun RequestValidationConfig.customerValidation() {
    validateCreateCustomerDto()
    validateUpdateCustomerDto()
}
