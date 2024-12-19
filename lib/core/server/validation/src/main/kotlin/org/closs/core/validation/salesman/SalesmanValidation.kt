package org.closs.core.validation.salesman

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.salesman.dtos.validateCreateSalesmanDto
import org.closs.core.validation.salesman.dtos.validateCreateStatisticsSalesmanDto
import org.closs.core.validation.salesman.dtos.validateUpdateSalesmanDto
import org.closs.core.validation.salesman.dtos.validateUpdateStatisticsSalesmanDto

fun RequestValidationConfig.salesmanValidation() {
    validateCreateSalesmanDto()
    validateUpdateSalesmanDto()
    validateCreateStatisticsSalesmanDto()
    validateUpdateStatisticsSalesmanDto()
}
