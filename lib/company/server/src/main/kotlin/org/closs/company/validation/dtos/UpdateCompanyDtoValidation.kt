package org.closs.company.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.company.UpdateCompanyDto

fun RequestValidationConfig.validateUpdateCompanyDto() {
    validate<UpdateCompanyDto> { dto ->
        when {
            dto.code.isEmpty() -> ValidationResult.Invalid("Code must not be empty.")
            dto.name.isEmpty() -> ValidationResult.Invalid("Name must not be empty.")
            dto.status < 0 -> ValidationResult.Invalid("Status must not be negative.")
            dto.domain.isEmpty() -> ValidationResult.Invalid("Domain must not be empty.")
            dto.agency.isEmpty() -> ValidationResult.Invalid("Agency must not be empty.")
            else -> ValidationResult.Valid
        }
    }
}
