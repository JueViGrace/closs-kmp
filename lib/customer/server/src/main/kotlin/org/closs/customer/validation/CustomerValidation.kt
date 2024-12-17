package org.closs.customer.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.customer.validation.dtos.validateCreateCustomerDto
import org.closs.customer.validation.dtos.validateCustomerByCodeDto
import org.closs.customer.validation.dtos.validateUpdateCustomerDto

fun RequestValidationConfig.customerValidation() {
    validateCreateCustomerDto()
    validateUpdateCustomerDto()
    validateCustomerByCodeDto()
}
