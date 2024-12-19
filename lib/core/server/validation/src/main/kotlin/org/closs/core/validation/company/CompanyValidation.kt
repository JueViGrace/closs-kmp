package org.closs.core.validation.company

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.company.dtos.validateCreateCompanyDto

fun RequestValidationConfig.companyValidation() {
    validateCreateCompanyDto()
}
