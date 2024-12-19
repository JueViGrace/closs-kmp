package org.closs.order.validation.auth

import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation

suspend fun searchByOrderData(
    user: UserIdValidation,
    orderCode: String,
    dbHelper: DbHelper
): Boolean {
    return when (user.role) {
        Role.SALESMAN -> {
            dbHelper.withDatabase { db ->
                executeOne(
                    query = db.authenticationQueriesQueries.findOrderByCodeForSalesman(
                        doc = orderCode,
                        code = user.code
                    )
                )
            } != null
        }
        Role.MANAGER -> {
            dbHelper.withDatabase { db ->
                executeOne(
                    query = db.authenticationQueriesQueries.findOrderByCodeForManager(
                        doc = orderCode,
                        code = user.code
                    )
                )
            } != null
        }
        else -> false
    }
}

suspend fun createOrderData(user: UserIdValidation, dto: CreateOrderDto, dbHelper: DbHelper): Boolean {
    return when (user.role) {
        Role.SALESMAN -> {
            if (dto.ktiCodven != user.code) {
                return false
            }
            val customer = dbHelper.withDatabase { db ->
                executeOne(
                    db.clossCustomerQueries.findCustomerByCode(
                        code = dto.ktiCodcli
                    )
                )
            } ?: return false

            when {
                customer.vendedor != user.code -> false
                customer.status.toInt() != 1 -> false
                else -> true
            }
        }
        Role.MANAGER -> {
            val salesmanCode = dbHelper.withDatabase { db ->
                executeOne(
                    query = db.authenticationQueriesQueries.findSalesmanWithManager(
                        salesman = dto.ktiCodven,
                        manager = user.code
                    )
                )
            } ?: return false

            val customer = dbHelper.withDatabase { db ->
                executeOne(
                    db.clossCustomerQueries.findCustomerByCode(
                        code = dto.ktiCodcli
                    )
                )
            } ?: return false

            when {
                customer.vendedor != salesmanCode -> false
                customer.status.toInt() != 1 -> false
                else -> true
            }
        }
        else -> false
    }
}
