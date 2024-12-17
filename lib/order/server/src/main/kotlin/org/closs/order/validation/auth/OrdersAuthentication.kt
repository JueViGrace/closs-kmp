package org.closs.order.validation.auth

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import kotlinx.serialization.json.Json
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.shared.types.order.OrderByIdDto
import org.closs.core.shared.types.order.OrdersByUserDto
import org.closs.core.types.OrderDataValidation
import org.closs.core.types.UserIdValidation
import org.closs.core.util.Jwt

fun AuthenticationConfig.ordersAuth(
    name: String,
    jwt: Jwt,
    userCall: suspend (String) -> UserIdValidation?,
    orderCall: suspend (String) -> OrderDataValidation?
) {
    jwt(name = name) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val tokenId = extractId(credential) ?: return@validateCredential null

                val user: UserIdValidation = userCall(tokenId) ?: return@validateCredential null

                if (user.isAdmin) {
                    return@validateCredential JWTPrincipal(credential.payload)
                }

                when (val body: Any = Json.decodeFromString(receive<String>())) {
                    is OrdersByUserDto -> {
                        if (user.code != body.code) {
                            return@validateCredential null
                        }
                    }
                    is OrderByIdDto -> {
                        val order = orderCall(body.ktiNdoc) ?: return@validateCredential null
                        if (user.code != order.salesman) {
                            return@validateCredential null
                        }
                    }
                    // todo: this blocks managers codes from doing orders for their salesmen
                    is CreateOrderDto -> {
                        if (user.code != body.ktiCodven) {
                            return@validateCredential null
                        }
                    }
                }

                JWTPrincipal(credential.payload)
            }
        }
    }
}
