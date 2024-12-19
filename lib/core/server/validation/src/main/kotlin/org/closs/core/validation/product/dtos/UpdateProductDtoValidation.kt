package org.closs.core.validation.product.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.product.UpdateProductDto

fun RequestValidationConfig.validateUpdateProductDto() {
    validate<UpdateProductDto> { dto ->
        when {
            dto.codigo.isBlank() -> ValidationResult.Invalid("Codigo cannot be blank")
            dto.grupo.isBlank() -> ValidationResult.Invalid("Grupo cannot be blank")
            dto.subgrupo.isBlank() -> ValidationResult.Invalid("Subgrupo cannot be blank")
            dto.nombre.isBlank() -> ValidationResult.Invalid("Nombre cannot be blank")
            dto.referencia.isBlank() -> ValidationResult.Invalid("Referencia cannot be blank")
            dto.marca.isBlank() -> ValidationResult.Invalid("Marca cannot be blank")
            dto.unidad.isBlank() -> ValidationResult.Invalid("Unidad cannot be blank")
            dto.discont < 0 -> ValidationResult.Invalid("Descuento cannot be negative")
            dto.existencia < 0 -> ValidationResult.Invalid("Existencia cannot be negative")
            dto.vtaMax < 0 -> ValidationResult.Invalid("Venta maxima cannot be negative")
            dto.vtaMin < 0 -> ValidationResult.Invalid("Venta minima cannot be negative")
            dto.vtaMinEx < 0 -> ValidationResult.Invalid("Venta minima exenta cannot be negative")
            dto.comprometido < 0 -> ValidationResult.Invalid("Comprometido cannot be negative")
            dto.precio1 < 0 -> ValidationResult.Invalid("Precio 1 cannot be negative")
            dto.precio2 < 0 -> ValidationResult.Invalid("Precio 2 cannot be negative")
            dto.precio3 < 0 -> ValidationResult.Invalid("Precio 3 cannot be negative")
            dto.precio4 < 0 -> ValidationResult.Invalid("Precio 4 cannot be negative")
            dto.precio5 < 0 -> ValidationResult.Invalid("Precio 5 cannot be negative")
            dto.precio6 < 0 -> ValidationResult.Invalid("Precio 6 cannot be negative")
            dto.precio7 < 0 -> ValidationResult.Invalid("Precio 7 cannot be negative")
            dto.preventa < 0 -> ValidationResult.Invalid("Preventa cannot be negative")
            dto.vtaSoloFac < 0 -> ValidationResult.Invalid("Venta solo factura cannot be negative")
            dto.vtaSoloNe < 0 -> ValidationResult.Invalid("Venta solo neto cannot be negative")
            dto.codBarras < 0 -> ValidationResult.Invalid("Codigo de barras cannot be negative")
            dto.cantBulto < 0 -> ValidationResult.Invalid("Cantidad de bulto cannot be negative")
            dto.costoProm < 0 -> ValidationResult.Invalid("Costo promedio cannot be negative")
            dto.util1.isBlank() -> ValidationResult.Invalid("Util 1 cannot be blank")
            dto.util2.isBlank() -> ValidationResult.Invalid("Util 2 cannot be blank")
            dto.util3.isBlank() -> ValidationResult.Invalid("Util 3 cannot be blank")
            dto.fchUltComp.isBlank() -> ValidationResult.Invalid("Fecha ultima compra cannot be blank")
            dto.qtyUltComp.isBlank() -> ValidationResult.Invalid("Cantidad ultima compra cannot be blank")
            dto.images.isEmpty() -> ValidationResult.Invalid("Images cannot be empty")
            else -> ValidationResult.Valid
        }
    }
}
