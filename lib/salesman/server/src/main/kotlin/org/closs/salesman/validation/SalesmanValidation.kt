package org.closs.salesman.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.salesman.validation.dtos.validateCreateSalesmanDto
import org.closs.salesman.validation.dtos.validateCreateStatisticsSalesmanDto
import org.closs.salesman.validation.dtos.validateUpdateSalesmanDto
import org.closs.salesman.validation.dtos.validateUpdateStatisticsSalesmanDto

fun RequestValidationConfig.salesmanValidation() {
    validateCreateSalesmanDto()
    validateUpdateSalesmanDto()
    validateCreateStatisticsSalesmanDto()
    validateUpdateStatisticsSalesmanDto()
}
