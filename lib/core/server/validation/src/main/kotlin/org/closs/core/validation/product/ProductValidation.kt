package org.closs.core.validation.product

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.product.dtos.validateCreateProductDto
import org.closs.core.validation.product.dtos.validateUpdateProductDto

fun RequestValidationConfig.productValidation() {
    validateCreateProductDto()
    validateUpdateProductDto()
}
