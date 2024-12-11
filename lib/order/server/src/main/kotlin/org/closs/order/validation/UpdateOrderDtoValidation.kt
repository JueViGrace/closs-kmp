package org.closs.order.validation

import org.closs.core.shared.types.order.UpdateOrderDto
import org.closs.core.shared.types.order.OrderStatus
import org.closs.core.util.Util.validUuid
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateUpdateOrderDto() {
    validate<UpdateOrderDto> { dto ->
        when {
            dto.id.isBlank() -> ValidationResult.Invalid("Id cannot be blank")
            !validUuid(dto.id) -> ValidationResult.Invalid("Id is not valid")
            dto.status.isBlank() -> ValidationResult.Invalid("Status cannot be blank")
            OrderStatus.entries.toTypedArray().none { status ->
                status.value == dto.status
            } -> ValidationResult.Invalid("Status is not valid")
            else -> ValidationResult.Valid
        }
    }
}
