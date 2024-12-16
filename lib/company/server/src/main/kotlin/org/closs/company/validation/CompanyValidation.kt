package org.closs.company.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.company.validation.dtos.validateCompanyByCodeDto
import org.closs.company.validation.dtos.validateCreateCompanyDto
import org.closs.company.validation.dtos.validateUpdateCompanyDto

fun RequestValidationConfig.companyValidation() {
    validateCreateCompanyDto()
    validateUpdateCompanyDto()
    validateCompanyByCodeDto()
}
