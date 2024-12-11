package org.closs.core.api.validation

import org.closs.auth.validation.authValidation
import org.closs.order.validation.orderValidation
import org.closs.product.validation.productValidation
import org.closs.user.validation.userValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.serverRequestValidation() {
    authValidation()
    userValidation()
    productValidation()
    orderValidation()
}
