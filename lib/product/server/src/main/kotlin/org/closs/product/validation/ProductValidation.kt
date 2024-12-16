package org.closs.product.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.product.validation.dtos.validateCreateProductDto
import org.closs.product.validation.dtos.validateUpdateProductDto

fun RequestValidationConfig.productValidation() {
    validateCreateProductDto()
    validateUpdateProductDto()
}
