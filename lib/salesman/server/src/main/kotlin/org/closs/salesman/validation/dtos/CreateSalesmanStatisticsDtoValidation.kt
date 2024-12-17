package org.closs.salesman.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.salesman.CreateSalesmanStatisticDto

fun RequestValidationConfig.validateCreateStatisticsSalesmanDto() {
    validate<CreateSalesmanStatisticDto> { dto ->
        when {
            dto.codcoord.isBlank() -> ValidationResult.Invalid("Código de coordinador cannot be empty")
            dto.nomcoord.isBlank() -> ValidationResult.Invalid("Nombre de coordinador cannot be empty")
            dto.vendedor.isBlank() -> ValidationResult.Invalid("Vendedor cannot be empty")
            dto.nombrevend.isBlank() -> ValidationResult.Invalid("Nombre del vendedor cannot be empty")
            dto.cntpedidos < 0 -> ValidationResult.Invalid("Cantidad de pedidos cannot be negative")
            dto.mtopedidos < 0 -> ValidationResult.Invalid("Monto de pedidos cannot be negative")
            dto.cntfacturas < 0 -> ValidationResult.Invalid("Cantidad de facturas cannot be negative")
            dto.mtofacturas < 0 -> ValidationResult.Invalid("Monto de facturas cannot be negative")
            dto.metavend < 0 -> ValidationResult.Invalid("Meta de ventas cannot be negative")
            dto.prcmeta < 0 -> ValidationResult.Invalid("Porcentaje de meta de ventas cannot be negative")
            dto.cntclientes < 0 -> ValidationResult.Invalid("Cantidad de clientes cannot be negative")
            dto.clivisit < 0 -> ValidationResult.Invalid("Cantidad de clientes visitados cannot be negative")
            dto.prcvisitas < 0 -> ValidationResult.Invalid("Porcentaje de visitas de clientes cannot be negative")
            dto.lomMontovtas < 0 -> ValidationResult.Invalid("LomMontovtas cannot be negative")
            dto.lomPrcvtas < 0 -> ValidationResult.Invalid("LomPrcvtas cannot be negative")
            dto.lomPrcvisit < 0 -> ValidationResult.Invalid("LomPrcvisit de clientes cannot be negative")
            dto.rlomMontovtas < 0 -> ValidationResult.Invalid("RlmMontovtas cannot be negative")
            dto.rlomPrcvtas < 0 -> ValidationResult.Invalid("RlomPrcvtas cannot be negative")
            dto.rlomPrcvisit < 0 -> ValidationResult.Invalid("RlomPrcvisit de clientes cannot be negative")
            dto.fechaEstad.isBlank() -> ValidationResult.Invalid("FechaEstad cannot be empty")
            dto.ppgdolTotneto < 0 -> ValidationResult.Invalid("PpgdolTotneto cannot be negative")
            dto.devdolTotneto < 0 -> ValidationResult.Invalid("DevdolTotneto cannot be negative")
            dto.defdolTotneto < 0 -> ValidationResult.Invalid("DefdolTotneto cannot be negative")
            dto.totdolcob < 0 -> ValidationResult.Invalid("Totdolcob cannot be negative")
            dto.cntrecl < 0 -> ValidationResult.Invalid("Cntrecl cannot be negative")
            dto.mtorecl < 0 -> ValidationResult.Invalid("Mtorecl cannot be negative")
            else -> ValidationResult.Valid
        }
    }
}
