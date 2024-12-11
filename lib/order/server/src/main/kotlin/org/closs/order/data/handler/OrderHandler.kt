package org.closs.order.data.handler

import kotlinx.coroutines.withContext
import org.closs.core.shared.types.order.OrderDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.order.data.storage.OrderStorage
import kotlin.coroutines.CoroutineContext

interface OrderHandler {
    suspend fun getOrders(): APIResponse<List<OrderDto>>
    suspend fun getOrderWithLines(id: String): APIResponse<OrderDto?>
    suspend fun getOrdersByUser(userId: String): APIResponse<List<OrderDto>>
    suspend fun getOrderByUserWithLines(orderId: String, userId: String): APIResponse<OrderDto?>
}

class DefaultOrderHandler(
    private val coroutineContext: CoroutineContext,
    private val storage: OrderStorage
) : OrderHandler {
    override suspend fun getOrders(): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getOrders()

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "No orders were found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrderWithLines(id: String): APIResponse<OrderDto?> {
        return withContext(coroutineContext) {
            val result = storage.getOrderWithLines(id)
                ?: return@withContext ServerResponse.notFound(
                    message = "Order with id $id was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrdersByUser(userId: String): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getOrdersByUser(userId)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "No orders were found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrderByUserWithLines(
        orderId: String,
        userId: String,
    ): APIResponse<OrderDto?> {
        return withContext(coroutineContext) {
            val result = storage.getOrderByUserWithLines(orderId, userId)
                ?: return@withContext ServerResponse.notFound(
                    message = "Order with id $orderId was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
