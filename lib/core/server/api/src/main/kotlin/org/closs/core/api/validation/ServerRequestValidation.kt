package org.closs.core.api.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.auth.validation.authValidation
import org.closs.company.validation.companyValidation
import org.closs.config.validation.configValidation
import org.closs.customer.validation.customerValidation
import org.closs.document.validation.documentValidation
import org.closs.manager.validation.managerValidation
import org.closs.order.validation.orderValidation
import org.closs.product.validation.productValidation
import org.closs.salesman.validation.salesmanValidation
import org.closs.user.validation.userValidation

fun RequestValidationConfig.serverRequestValidation() {
    authValidation()
    companyValidation()
    configValidation()
    userValidation()
    managerValidation()
    salesmanValidation()
    customerValidation()
    productValidation()
    orderValidation()
    documentValidation()
}
