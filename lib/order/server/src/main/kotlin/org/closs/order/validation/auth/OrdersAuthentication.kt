package org.closs.order.validation.auth

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import kotlinx.serialization.json.Json
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.shared.types.order.OrderByIdDto
import org.closs.core.shared.types.search.SearchByCustomerCodeDto
import org.closs.core.shared.types.search.SearchByManagerCodeDto
import org.closs.core.shared.types.search.SearchBySalesmanCodeDto
import org.closs.core.types.JwtAuthName
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation
import org.closs.core.util.Jwt

fun AuthenticationConfig.ordersAuth(
    jwt: Jwt,
    userCall: suspend (String) -> UserIdValidation?,
    searchBySalesmanCall: suspend (UserIdValidation, String) -> Boolean,
    searchByManagerCall: suspend (UserIdValidation, String) -> Boolean,
    searchByCustomerCall: suspend (UserIdValidation, String) -> Boolean,
    searchByIdCall: suspend (UserIdValidation, String) -> Boolean,
    createOrderCall: suspend (UserIdValidation, CreateOrderDto) -> Boolean,
) {
    jwt(name = JwtAuthName.ORDER_ROUTES.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val user: UserIdValidation = extractId(credential)?.let { id ->
                    userCall(id)
                } ?: return@validateCredential null

                val body: Any? = Json.decodeFromString(receive<String>())

                return@validateCredential when {
                    user.role == Role.ADMIN -> JWTPrincipal(credential.payload)
                    body is SearchBySalesmanCodeDto -> {
                        if (searchBySalesmanCall(user, body.code)) {
                            return@validateCredential JWTPrincipal(credential.payload)
                        }
                        null
                    }
                    body is SearchByManagerCodeDto -> {
                        if (searchByManagerCall(user, body.manager)) {
                            return@validateCredential JWTPrincipal(credential.payload)
                        }
                        null
                    }
                    body is SearchByCustomerCodeDto -> {
                        if (searchByCustomerCall(user, body.code)) {
                            return@validateCredential JWTPrincipal(credential.payload)
                        }
                        null
                    }
                    body is OrderByIdDto -> {
                        if (searchByIdCall(user, body.ktiNdoc)) {
                            return@validateCredential JWTPrincipal(credential.payload)
                        }
                        null
                    }
                    body is CreateOrderDto -> {
                        if (createOrderCall(user, body)) {
                            return@validateCredential JWTPrincipal(credential.payload)
                        }
                        null
                    }
                    else -> null
                }
            }
        }
    }
}
