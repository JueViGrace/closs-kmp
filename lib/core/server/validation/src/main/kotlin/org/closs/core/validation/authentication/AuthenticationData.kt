package org.closs.core.validation.authentication

import io.ktor.server.auth.jwt.JWTCredential
import org.closs.core.database.helper.DbHelper
import org.closs.core.types.JwtAuthName
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation
import org.closs.core.types.aliases.DbCustomer
import org.closs.core.types.aliases.DbDocument
import org.closs.core.types.aliases.DbOrder
import org.closs.core.util.Jwt
import org.closs.core.util.Util.verifyUserRole

object AuthenticationData {
    private suspend fun getUserData(id: String, dbHelper: DbHelper): UserIdValidation? {
        val user = dbHelper.withDatabase { db ->
            executeOne(db.clossUserQueries.findExistingUser(id))
        } ?: return null

        return UserIdValidation(
            role = verifyUserRole(user.role),
            userId = user.id,
            username = user.username,
            code = user.codigo,
        )
    }

    suspend fun Jwt.getUserId(credential: JWTCredential, dbHelper: DbHelper): UserIdValidation? {
        return extractId(credential)?.let { id ->
            getUserData(id, dbHelper)
        }
    }

    suspend fun searchAuth(
        authName: JwtAuthName,
        user: UserIdValidation,
        code: String,
        dbHelper: DbHelper
    ): Boolean {
        return when (authName) {
            JwtAuthName.SALESMAN_CODE -> {
                roleSearch(user, code, dbHelper)
            }
            JwtAuthName.CUSTOMER_CODE -> {
                val customer = customerSearch(code, dbHelper)
                    ?: return false
                roleSearch(user, customer.vendedor, dbHelper)
            }
            JwtAuthName.ORDER_CODE -> {
                val order = orderSearch(code, dbHelper)
                    ?: return false
                roleSearch(user, order.kti_codven, dbHelper)
            }
            JwtAuthName.DOCUMENT_CODE -> {
                val document = documentSearch(code, dbHelper)
                    ?: return false
                roleSearch(user, document.vendedor, dbHelper)
            }
            else -> false
        }
    }

    private suspend fun roleSearch(user: UserIdValidation, code: String, dbHelper: DbHelper): Boolean {
        return when (user.role) {
            Role.SALESMAN -> {
                salesmanSearch(user, code)
            }
            Role.MANAGER -> {
                managerSearch(user, code, dbHelper) != null
            }
            Role.ADMIN -> true
            else -> false
        }
    }

    private suspend fun managerSearch(
        user: UserIdValidation,
        code: String,
        dbHelper: DbHelper
    ): String? {
        return dbHelper.withDatabase { db ->
            executeOne(
                db.authenticationQueriesQueries.findSalesmanForManager(
                    manager = user.code,
                    salesman = code
                )
            )
        }
    }

    private fun salesmanSearch(user: UserIdValidation, code: String): Boolean {
        return user.code == code
    }

    private suspend fun customerSearch(code: String, dbHelper: DbHelper): DbCustomer? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossCustomerQueries.findCustomerByCode(code)
            )
        }
    }

    private suspend fun orderSearch(code: String, dbHelper: DbHelper): DbOrder? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossOrderQueries.findOrder(code)
            )
        }
    }

    private suspend fun documentSearch(code: String, dbHelper: DbHelper): DbDocument? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossDocumentQueries.findDocument(code)
            )
        }
    }
}
