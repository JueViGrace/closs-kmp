package org.closs.core.validation.customer.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.customer.CreateCustomerDto

fun RequestValidationConfig.validateCreateCustomerDto() {
    validate<CreateCustomerDto> { dto ->
        when {
            dto.codigo.isBlank() -> ValidationResult.Invalid("Codigo cannot be empty")
            dto.nombre.isBlank() -> ValidationResult.Invalid("Nombre cannot be empty")
            dto.email.isBlank() -> ValidationResult.Invalid("Email cannot be empty")
            dto.direccion.isBlank() -> ValidationResult.Invalid("Direccion cannot be empty")
            dto.telefonos.isBlank() -> ValidationResult.Invalid("Telefonos cannot be empty")
            dto.perscont.isBlank() -> ValidationResult.Invalid("Perscont cannot be empty")
            dto.vendedor.isBlank() -> ValidationResult.Invalid("Vendedor cannot be empty")
            dto.contribspecial < 0 -> ValidationResult.Invalid("Contribspecial cannot be negative")
            dto.status < 0 -> ValidationResult.Invalid("Status cannot be negative")
            dto.sector.isBlank() -> ValidationResult.Invalid("Sector cannot be empty")
            dto.subsector.isBlank() -> ValidationResult.Invalid("Subsector cannot be empty")
            dto.precio < 0 -> ValidationResult.Invalid("Precio cannot be negative")
            dto.kneActiva < 0 -> ValidationResult.Invalid("KneActiva cannot be negative")
            dto.noemifac < 0 -> ValidationResult.Invalid("Noemifac cannot be negative")
            dto.noeminota < 0 -> ValidationResult.Invalid("Noeminota cannot be negative")
            dto.fchultvta.isBlank() -> ValidationResult.Invalid("Fchultvta cannot be empty")
            dto.mtoultvta.isNaN() -> ValidationResult.Invalid("Mtoultvta cannot be empty")
            dto.prcdpagdia.isNaN() -> ValidationResult.Invalid("Prcdpagdia cannot be empty")
            dto.promdiasp.isNaN() -> ValidationResult.Invalid("Promdiasp cannot be empty")
            dto.riesgocrd.isNaN() -> ValidationResult.Invalid("Riesgocrd cannot be empty")
            dto.cantdocs.isNaN() -> ValidationResult.Invalid("Cantdocs cannot be empty")
            dto.totmtodocs.isNaN() -> ValidationResult.Invalid("Totmtodocs cannot be empty")
            dto.prommtodoc.isNaN() -> ValidationResult.Invalid("Prommtodoc cannot be empty")
            dto.diasultvta.isNaN() -> ValidationResult.Invalid("Diasultvta cannot be empty")
            dto.promdiasvta.isNaN() -> ValidationResult.Invalid("Promdiasvta cannot be empty")
            dto.limcred.isNaN() -> ValidationResult.Invalid("Limcred cannot be empty")
            dto.fchcrea.isBlank() -> ValidationResult.Invalid("Fchcrea cannot be empty")
            dto.dolarflete < 0 -> ValidationResult.Invalid("Dolarflete cannot be negative")
            dto.nodolarflete < 0 -> ValidationResult.Invalid("Nodolarflete cannot be negative")
            else -> ValidationResult.Valid
        }
    }
}
