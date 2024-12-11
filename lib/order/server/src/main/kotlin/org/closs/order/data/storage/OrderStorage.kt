package org.closs.order.data.storage

import kotlinx.coroutines.CoroutineScope
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.order.OrderDto
import org.closs.core.types.order.orderByUserToDto
import org.closs.core.types.order.orderLinesToDto

interface OrderStorage {
    suspend fun getOrders(): List<OrderDto>
    suspend fun getOrderWithLines(id: String): OrderDto?
    suspend fun getOrdersByUser(userId: String): List<OrderDto>
    suspend fun getOrderByUserWithLines(orderId: String, userId: String): OrderDto?
}

class DefaultOrderStorage(
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : OrderStorage {
    override suspend fun getOrders(): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            dbHelper.executeList(
                query = db.clossOrderQueries.findOrders()
            ).map { order ->
                order.orderLinesToDto()
            }
        }
    }

    override suspend fun getOrderWithLines(id: String): OrderDto? {
        return dbHelper.withDatabase { db ->
            dbHelper.executeList(
                query = db.clossOrderQueries.findOrderWithLines(id)
            ).orderLinesToDto()
        }
    }

    override suspend fun getOrdersByUser(userId: String): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            dbHelper.executeList(
                query = db.clossOrderQueries.findOrdersByUser(userId)
            ).map { order ->
                order.orderLinesToDto()
            }
        }
    }

    override suspend fun getOrderByUserWithLines(orderId: String, userId: String): OrderDto? {
        return dbHelper.withDatabase { db ->
            dbHelper.executeList(
                query = db.clossOrderQueries.findOrderByUserWithLines(
                    id = orderId,
                    user_id = userId
                )
            ).orderByUserToDto()
        }
    }
}
