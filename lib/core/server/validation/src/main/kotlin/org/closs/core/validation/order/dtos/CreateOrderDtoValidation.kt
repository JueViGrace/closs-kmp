package org.closs.core.validation.order.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.order.CreateOrderDto

fun RequestValidationConfig.validateCreateOrderDto() {
    validate<CreateOrderDto> { dto ->
        when {
            dto.ktiNdoc.isEmpty() -> ValidationResult.Invalid("Document number cannot be empty")
            dto.ktiTdoc.isEmpty() -> ValidationResult.Invalid("Document type cannot be empty")
            dto.ktiCodcli.isEmpty() -> ValidationResult.Invalid("Customer code cannot be empty")
            dto.ktiNombrecli.isEmpty() -> ValidationResult.Invalid("Customer name cannot be empty")
            dto.ktiCodven.isEmpty() -> ValidationResult.Invalid("Salesman code cannot be empty")
            dto.ktiDocsol.isEmpty() -> ValidationResult.Invalid("Salesman name cannot be empty")
            dto.ktiCondicion.isEmpty() -> ValidationResult.Invalid("Condition cannot be empty")
            dto.ktiTipprec < 0 -> ValidationResult.Invalid("Price type must not be negative")
            dto.ktiTotneto.isEmpty() -> ValidationResult.Invalid("Total amount cannot be empty")
            dto.ktiStatus < 0 -> ValidationResult.Invalid("Status must not be negative")
            dto.ktiNroped.isEmpty() -> ValidationResult.Invalid("Order number cannot be empty")
            dto.ktiFchdoc.isEmpty() -> ValidationResult.Invalid("Document date cannot be empty")
            dto.ktiNegesp < 0 -> ValidationResult.Invalid("Negative special must not be negative")
            dto.kePedstatus < 0 -> ValidationResult.Invalid("Ped status must not be negative")
            dto.dolarFlete < 0 -> ValidationResult.Invalid("Dolar flete must not be negative")
            dto.complemento < 0 -> ValidationResult.Invalid("Complemento must not be negative")
            dto.nroComplemento.isEmpty() -> ValidationResult.Invalid("Nro complemento cannot be empty")
            dto.details.isEmpty() -> ValidationResult.Invalid("Details cannot be empty")
            else -> {
                return@validate dto.details.map { detail ->
                    if (detail.ktiTdoc.isEmpty()) {
                        return@map ValidationResult.Invalid("Document type cannot be empty")
                    }
                    if (detail.ktiNdoc.isEmpty()) {
                        return@map ValidationResult.Invalid("Product id cannot be empty")
                    }
                    if (detail.ktiTipprec.isEmpty()) {
                        return@map ValidationResult.Invalid("Price type cannot be empty")
                    }
                    if (detail.kmvCodart.isEmpty()) {
                        return@map ValidationResult.Invalid("Product code cannot be empty")
                    }
                    if (detail.kmvNombre.isEmpty()) {
                        return@map ValidationResult.Invalid("Product name cannot be empty")
                    }
                    if (detail.kmvCant < 0) {
                        return@map ValidationResult.Invalid("Product quantity cannot be negative")
                    }
                    if (detail.kmvArtprec < 0) {
                        return@map ValidationResult.Invalid("Product price cannot be negative")
                    }
                    if (detail.kmvStot < 0) {
                        return@map ValidationResult.Invalid("Product total cannot be negative")
                    }
                    if (detail.kmvDctolin < 0) {
                        return@map ValidationResult.Invalid("Product discount cannot be negative")
                    }
                    ValidationResult.Valid
                }.firstOrNull { it is ValidationResult.Invalid } ?: ValidationResult.Valid
            }
        }
    }
}
