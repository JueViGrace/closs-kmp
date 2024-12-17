package org.closs.core.api.validation

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import org.closs.core.database.helper.DbHelper
import org.closs.core.types.JwtAuthName
import org.closs.core.types.OrderDataValidation
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation
import org.closs.core.util.Jwt
import org.closs.order.validation.auth.ordersAuth
import org.closs.user.validation.auth.userRoutesAuth

fun AuthenticationConfig.serverAuthValidation(jwt: Jwt, dbHelper: DbHelper) {
    jwt(JwtAuthName.SESSION.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                extractId(credential)?.let { id ->
                    dbHelper.withDatabase { db ->
                        executeOne(db.clossTokenQueries.findTokenById(id))
                    }
                } ?: return@validateCredential null

                JWTPrincipal(credential.payload)
            }
        }
    }

    jwt(JwtAuthName.ADMIN.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val user = extractId(credential)?.let { id ->
                    getUser(id, dbHelper)
                }
                if (user == null) {
                    return@validateCredential null
                }

                if (user.isAdmin) {
                    return@validateCredential JWTPrincipal(credential.payload)
                }

                null
            }
        }
    }

    userRoutesAuth(
        name = JwtAuthName.USER_ROUTES.value,
        jwt = jwt,
        userCall = { id ->
            getUser(id, dbHelper)
        }
    )

    ordersAuth(
        name = JwtAuthName.ORDER_ROUTES.value,
        jwt = jwt,
        userCall = { id ->
            getUser(id, dbHelper)
        },
        orderCall = { id ->
            // todo: change query to find managers
            val order = dbHelper.withDatabase { db ->
                executeOne(db.clossOrderQueries.findOrder(id))
            }
            if (order == null) {
                return@ordersAuth null
            }

            OrderDataValidation(
                document = order.kti_ndoc,
                salesman = order.kti_codven,
                customer = order.kti_codcli
            )
        }
    )
}

private suspend fun getUser(id: String, dbHelper: DbHelper): UserIdValidation? {
    val dbUser = dbHelper.withDatabase { db ->
        executeOne(
            query = db.clossUserQueries.findExistingUser(id)
        )
    }

    if (dbUser == null) {
        return null
    }

    return UserIdValidation(
        isAdmin = Role.valueOf(dbUser.role) == Role.ADMIN,
        userId = dbUser.id,
        username = dbUser.username,
        code = dbUser.codigo
    )
}
