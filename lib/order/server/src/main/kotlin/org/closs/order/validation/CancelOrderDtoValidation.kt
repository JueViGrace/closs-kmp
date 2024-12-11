package org.closs.order.validation

import org.closs.core.shared.types.order.OrderStatus
import org.closs.core.util.Util.validUuid
import org.closs.core.shared.types.order.UpdateOrderDto
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateCancelOrderDto() {
    validate<UpdateOrderDto> { dto ->
        when {
            dto.id.isBlank() -> ValidationResult.Invalid("Id cannot be blank")
            !validUuid(dto.id) -> ValidationResult.Invalid("Id is not valid")
            dto.status.isBlank() -> ValidationResult.Invalid("Status cannot be blank")
            OrderStatus.valueOf(dto.status) != OrderStatus.Cancelled -> ValidationResult.Invalid("Status is not valid")
            else -> ValidationResult.Valid
        }
    }
}
