package org.closs.core.api.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.auth.validation.authValidation
import org.closs.company.validation.companyValidation
import org.closs.config.validation.configValidation
import org.closs.order.validation.orderValidation
import org.closs.product.validation.productValidation
import org.closs.user.validation.userValidation

fun RequestValidationConfig.serverRequestValidation() {
    authValidation()
    companyValidation()
    configValidation()
    userValidation()
    productValidation()
    orderValidation()
}
