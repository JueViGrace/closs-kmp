package org.closs.core.validation.order

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.order.dtos.validateCreateOrderDto

fun RequestValidationConfig.orderValidation() {
    validateCreateOrderDto()
}
