package org.closs.product.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.productValidation() {
    validateCreateProductDto()
    validateUpdateProductDto()
}
