package org.closs.order.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.order.validation.dtos.validateCreateOrderDto
import org.closs.order.validation.dtos.validateOrderById

fun RequestValidationConfig.orderValidation() {
    validateCreateOrderDto()
    validateOrderById()
}
