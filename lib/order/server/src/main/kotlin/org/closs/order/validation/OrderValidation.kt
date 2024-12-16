package org.closs.order.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.orderValidation() {
    validateCreateOrderDto()
    validateOrderById()
    validateOrderByUser()
}
