package org.closs.core.api.validation.authentication

import org.closs.core.database.helper.DbHelper
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation

suspend fun searchBySalesmanData(user: UserIdValidation, code: String, dbHelper: DbHelper): Boolean {
    val salesman = dbHelper.withDatabase { db ->
        executeOne(
            query = db.clossSalesmanQueries.findSalesman(code)
        )
    } ?: return false

    return when {
        salesman.status.toInt() != 1 -> false
        user.role == Role.SALESMAN -> {
            salesman.codigo == user.code
        }
        user.role == Role.MANAGER -> {
            dbHelper.withDatabase { db ->
                executeOne(
                    query = db.authenticationQueriesQueries.findSalesmanWithManager(
                        salesman = code,
                        manager = user.code
                    )
                ) != null
            }
        }
        else -> false
    }
}
