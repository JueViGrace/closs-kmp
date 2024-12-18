package org.closs.core.api.validation.authentication

import org.closs.core.database.helper.DbHelper
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation

suspend fun searchByCustomerData(
    user: UserIdValidation,
    code: String,
    dbHelper: DbHelper
): Boolean {
    val customer = dbHelper.withDatabase { db ->
        executeOne(
            query = db.clossCustomerQueries.findCustomerByCode(code)
        )
    } ?: return false

    return when {
        customer.status.toInt() != 1 -> false
        user.role == Role.SALESMAN -> {
            customer.vendedor == user.code
        }
        user.role == Role.MANAGER -> {
            dbHelper.withDatabase { db ->
                executeOne(
                    query = db.authenticationQueriesQueries.findSalesmanWithManager(
                        manager = user.code,
                        salesman = customer.vendedor
                    )
                )
            } != null
        }
        else -> true
    }
}
