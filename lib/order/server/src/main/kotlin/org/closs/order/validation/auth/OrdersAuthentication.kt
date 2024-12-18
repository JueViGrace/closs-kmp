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
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation
import org.closs.core.util.Jwt

fun AuthenticationConfig.ordersAuth(
    name: String,
    jwt: Jwt,
    userCall: suspend (String) -> UserIdValidation?,
    searchBySalesmanCall: suspend (UserIdValidation, String) -> Boolean,
    searchByManagerCall: suspend (UserIdValidation, String) -> Boolean,
    searchByCustomerCall: suspend (UserIdValidation, String) -> Boolean,
    searchByIdCall: suspend (UserIdValidation, String) -> Boolean,
    createOrderCall: suspend (UserIdValidation, CreateOrderDto) -> Boolean,
) {
    jwt(name = name) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val tokenId = extractId(credential) ?: return@validateCredential null

                val user: UserIdValidation = userCall(tokenId) ?: return@validateCredential null

                if (user.role == Role.ADMIN) {
                    return@validateCredential JWTPrincipal(credential.payload)
                }

                when (val body: Any = Json.decodeFromString(receive<String>())) {
                    is SearchBySalesmanCodeDto -> {
                        if (!searchBySalesmanCall(user, body.code)) {
                            return@validateCredential null
                        }
                    }
                    is SearchByManagerCodeDto -> {
                        if (!searchByManagerCall(user, body.manager)) {
                            return@validateCredential null
                        }
                    }
                    is SearchByCustomerCodeDto -> {
                        if (!searchByCustomerCall(user, body.code)) {
                            return@validateCredential null
                        }
                    }
                    is OrderByIdDto -> {
                        if (!searchByIdCall(user, body.ktiNdoc)) {
                            return@validateCredential null
                        }
                    }
                    is CreateOrderDto -> {
                        if (!createOrderCall(user, body)) {
                            return@validateCredential null
                        }
                    }
                }

                JWTPrincipal(credential.payload)
            }
        }
    }
}
