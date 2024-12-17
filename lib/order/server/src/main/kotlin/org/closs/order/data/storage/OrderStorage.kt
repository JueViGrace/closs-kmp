package org.closs.order.data.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.shared.types.order.OrderDto
import org.closs.core.types.order.dbOrderToDto
import org.closs.core.types.order.dtoToDbOrder
import org.closs.core.types.order.orderLinesToDto
import org.closs.core.types.order.toDbDetails

interface OrderStorage {
    suspend fun getOrder(doc: String): OrderDto?
    suspend fun getOrderWithLines(doc: String): OrderDto?
    suspend fun getAllOrdersByManager(code: String): List<OrderDto>
    suspend fun getOrdersByManager(code: String): List<OrderDto>
    suspend fun getAllOrdersBySalesman(code: String): List<OrderDto>
    suspend fun getOrdersBySalesman(code: String): List<OrderDto>
    suspend fun getAllOrdersByCustomer(code: String): List<OrderDto>
    suspend fun getOrdersByCustomer(code: String): List<OrderDto>
    suspend fun createOrder(dto: CreateOrderDto): OrderDto?
}

class DefaultOrderStorage(
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : OrderStorage {
    override suspend fun getOrder(doc: String): OrderDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossOrderQueries.findOrder(
                    doc = doc
                )
            )?.dbOrderToDto()
        }
    }

    override suspend fun getOrderWithLines(doc: String): OrderDto? {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossOrderQueries.findOrderWithLines(doc)
            ).orderLinesToDto()
        }
    }

    override suspend fun getAllOrdersByManager(code: String): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossOrderQueries.findAllOrdersByManager(
                    manager = code
                )
            ).map { row -> row.dbOrderToDto() }
        }
    }

    override suspend fun getOrdersByManager(code: String): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossOrderQueries.findOrdersByManager(
                    manager = code
                )
            ).map { row -> row.dbOrderToDto() }
        }
    }

    override suspend fun getAllOrdersBySalesman(code: String): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossOrderQueries.findAllOrdersBySalesman(
                    code = code
                )
            ).map { row -> row.dbOrderToDto() }
        }
    }

    override suspend fun getOrdersBySalesman(code: String): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossOrderQueries.findOrdersBySalesman(
                    code = code
                )
            ).map { row -> row.dbOrderToDto() }
        }
    }

    override suspend fun getAllOrdersByCustomer(code: String): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossOrderQueries.findAllOrdersByCustomer(
                    code = code
                )
            ).map { row -> row.dbOrderToDto() }
        }
    }

    override suspend fun getOrdersByCustomer(code: String): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossOrderQueries.findOrdersByCustomer(
                    code = code
                )
            ).map { row -> row.dbOrderToDto() }
        }
    }

    override suspend fun createOrder(dto: CreateOrderDto): OrderDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbOrder = db.clossOrderQueries
                        .insert(
                            closs_order = dto.dtoToDbOrder()
                        )
                        .executeAsOneOrNull()
                        ?: rollback(null)

                    val list = dto.details.map { detail ->
                        db.clossOrderLinesQueries.insert(detail.toDbDetails()).executeAsOneOrNull()
                    }
                    if (list.contains(null)) {
                        rollback(null)
                    }

                    getOrder(dbOrder.kti_ndoc)
                }
            }
        }.await()
    }
}
