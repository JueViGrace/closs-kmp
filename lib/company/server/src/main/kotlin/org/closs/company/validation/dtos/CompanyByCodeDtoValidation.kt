package org.closs.company.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.company.CompanyByCodeDto

fun RequestValidationConfig.validateCompanyByCodeDto() {
    validate<CompanyByCodeDto> { dto ->
        when {
            dto.code.isEmpty() -> ValidationResult.Invalid("Code must not be empty.")
            else -> ValidationResult.Valid
        }
    }
}
