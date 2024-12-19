package org.closs.core.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.auth.authValidation
import org.closs.core.validation.company.companyValidation
import org.closs.core.validation.config.configValidation
import org.closs.core.validation.customer.customerValidation
import org.closs.core.validation.document.documentValidation
import org.closs.core.validation.manager.managerValidation
import org.closs.core.validation.order.orderValidation
import org.closs.core.validation.product.productValidation
import org.closs.core.validation.salesman.salesmanValidation
import org.closs.core.validation.user.userValidation

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
