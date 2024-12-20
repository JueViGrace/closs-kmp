package org.closs.core.validation.company.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.company.CreateCompanyDto

fun RequestValidationConfig.validateCreateCompanyDto() {
    validate<CreateCompanyDto> { dto ->
        when {
            dto.name.isEmpty() -> ValidationResult.Invalid("Name must not be empty.")
            dto.code.isEmpty() -> ValidationResult.Invalid("Code must not be empty.")
            dto.domain.isEmpty() -> ValidationResult.Invalid("Domain must not be empty.")
            dto.agency.isEmpty() -> ValidationResult.Invalid("Agency must not be empty.")
            dto.status < 0 -> ValidationResult.Invalid("Status must not be negative.")
            else -> ValidationResult.Valid
        }
    }
}