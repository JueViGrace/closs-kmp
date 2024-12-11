package org.closs.core.types.order

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.order.CancelOrderDto
import org.closs.core.shared.types.order.CreateOrderDetailsDto
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.shared.types.order.OrderDetailsDto
import org.closs.core.shared.types.order.OrderDto
import org.closs.core.shared.types.order.OrderStatus
import org.closs.core.shared.types.order.UpdateOrderDto
import org.closs.core.types.aliases.DbOrder
import org.closs.core.types.aliases.OrderByUserWithLines
import org.closs.core.types.aliases.OrderLines
import org.closs.core.types.aliases.OrderWithLines
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun DbOrder.orderLinesToDto(): OrderDto = OrderDto(
    id = id,
    userId = user_id,
    createdAt = created_at,
    details = emptyList(),
)

// todo: this should be done in other way

fun List<OrderWithLines>.orderLinesToDto(): OrderDto? {
    val group: Map<OrderDto, List<OrderWithLines>> = this.groupBy { row ->
        OrderDto(
            id = row.id,
            userId = row.user_id,
            createdAt = row.created_at,
            details = emptyList(),
        )
    }

    val dto: OrderDto? = group.map { (order: OrderDto, rows: List<OrderWithLines>) ->
        return@map OrderDto(
            id = order.id,
            userId = order.userId,
            createdAt = order.createdAt,
            details = rows.orderLinesToDetails()
        )
    }.firstOrNull()

    return dto
}

fun List<OrderWithLines>.orderLinesToDetails(): List<OrderDetailsDto> {
    this.firstOrNull { row -> row.order_id == null } ?: return emptyList()

    val details: List<OrderDetailsDto> = this.map { row ->
        return@map OrderDetailsDto(
            orderId = row.order_id ?: "",
            productId = row.product_id ?: "",
            images = row.images?.split(",") ?: emptyList(),
        )
    }

    return details
}

fun List<OrderByUserWithLines>.orderByUserToDto(): OrderDto? {
    val group: Map<OrderDto, List<OrderByUserWithLines>> = this.groupBy { row ->
        OrderDto(
            id = row.id,
            userId = row.user_id,
            createdAt = row.created_at,
            details = emptyList(),
        )
    }

    val dto: OrderDto? = group.map { (order: OrderDto, rows: List<OrderByUserWithLines>) ->
        return@map OrderDto(
            id = order.id,
            userId = order.userId,
            createdAt = order.createdAt,
            details = rows.orderByUserToDetails()
        )
    }.firstOrNull()

    return dto
}

fun List<OrderByUserWithLines>.orderByUserToDetails(): List<OrderDetailsDto> {
    this.firstOrNull { row -> row.order_id == null } ?: return emptyList()

    val details: List<OrderDetailsDto> = this.map { row ->
        return@map OrderDetailsDto(
            orderId = row.order_id ?: "",
            productId = row.product_id ?: "",
            images = row.images?.split(",") ?: emptyList(),
        )
    }

    return details
}

@OptIn(ExperimentalUuidApi::class)
fun CreateOrderDto.toDb(): DbOrder = DbOrder(
    id = Uuid.random().toString(),
    user_id = userId,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
)

fun CreateOrderDetailsDto.toDbDetails(orderId: String): OrderLines = OrderLines(
    order_id = orderId,
    product_id = productId,
)

fun CancelOrderDto.toUpdateDto(): UpdateOrderDto = UpdateOrderDto(
    id = id,
    status = OrderStatus.Cancelled.value,
)
