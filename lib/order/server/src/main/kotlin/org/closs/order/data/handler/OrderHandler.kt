package org.closs.order.data.handler

import kotlinx.coroutines.withContext
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.shared.types.order.OrderDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.order.data.storage.OrderStorage
import kotlin.coroutines.CoroutineContext

interface OrderHandler {
    suspend fun getOrder(doc: String): APIResponse<OrderDto?>
    suspend fun getOrderWithLines(doc: String): APIResponse<OrderDto?>
    suspend fun getAllOrdersByManager(code: String): APIResponse<List<OrderDto>>
    suspend fun getOrdersByManager(code: String): APIResponse<List<OrderDto>>
    suspend fun getAllOrdersBySalesman(code: String): APIResponse<List<OrderDto>>
    suspend fun getOrdersBySalesman(code: String): APIResponse<List<OrderDto>>
    suspend fun getAllOrdersByCustomer(code: String): APIResponse<List<OrderDto>>
    suspend fun getOrdersByCustomer(code: String): APIResponse<List<OrderDto>>
    suspend fun createOrder(dto: CreateOrderDto): APIResponse<String>
}

class DefaultOrderHandler(
    private val coroutineContext: CoroutineContext,
    private val storage: OrderStorage
) : OrderHandler {
    override suspend fun getOrder(doc: String): APIResponse<OrderDto?> {
        return withContext(coroutineContext) {
            val result = storage.getOrderWithLines(doc)
                ?: return@withContext ServerResponse.notFound(
                    message = "Order with id $doc was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrderWithLines(doc: String): APIResponse<OrderDto?> {
        return withContext(coroutineContext) {
            val result = storage.getOrderWithLines(doc)
                ?: return@withContext ServerResponse.notFound(
                    message = "Order with id $doc was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getAllOrdersByManager(code: String): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getAllOrdersByManager(code)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Orders of manager $code were not found"
                )
            }
            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrdersByManager(code: String): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getAllOrdersByManager(code)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Orders of manager $code were not found"
                )
            }
            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getAllOrdersBySalesman(code: String): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getAllOrdersByManager(code)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Orders of salesman $code were not found"
                )
            }
            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrdersBySalesman(code: String): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getAllOrdersByManager(code)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Orders of salesman $code were not found"
                )
            }
            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getAllOrdersByCustomer(code: String): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getAllOrdersByManager(code)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Orders of customer $code were not found"
                )
            }
            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrdersByCustomer(code: String): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getAllOrdersByManager(code)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Orders of customer $code were not found"
                )
            }
            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createOrder(dto: CreateOrderDto): APIResponse<String> {
        return withContext(coroutineContext) {
            ServerResponse.ok(
                data = storage.createOrder(dto),
                message = "Processed successfully"
            )
        }
    }
}
