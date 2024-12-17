package org.closs.salesman.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.salesman.CreateSalesmanDto

fun RequestValidationConfig.validateUpdateSalesmanDto() {
    validate<CreateSalesmanDto> { dto ->
        when {
            dto.codigo.isBlank() -> ValidationResult.Invalid("Código cannot be empty")
            dto.nombre.isBlank() -> ValidationResult.Invalid("Nombre cannot be empty")
            dto.email.isBlank() -> ValidationResult.Invalid("Email cannot be empty")
            dto.telefono.isBlank() -> ValidationResult.Invalid("Teléfono cannot be empty")
            dto.telefonos.isBlank() -> ValidationResult.Invalid("Teléfonos cannot be empty")
            dto.supervpor.isBlank() -> ValidationResult.Invalid("Supervisor cannot be empty")
            dto.sector.isBlank() -> ValidationResult.Invalid("Sector cannot be empty")
            dto.subcodigo.isBlank() -> ValidationResult.Invalid("Subcódigo cannot be empty")
            dto.nivgcial < 0 -> ValidationResult.Invalid("Nivgcial cannot be negative")
            else -> ValidationResult.Valid
        }
    }
}
