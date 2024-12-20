package org.closs.core.validation.authentication

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import kotlinx.serialization.json.Json
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.types.APIResponse
import org.closs.core.types.JwtAuthName
import org.closs.core.types.ServerResponse
import org.closs.core.util.Jwt
import org.closs.core.validation.authentication.AuthenticationData.getUserId
import org.closs.core.validation.authentication.AuthenticationData.searchAuth

fun AuthenticationConfig.orderCodeAuth(
    name: JwtAuthName,
    jwt: Jwt,
    dbHelper: DbHelper
) {
    jwt(name = name.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val user = getUserId(credential, dbHelper)
                    ?: return@validateCredential null

                val queryCode = request.queryParameters["order"]
                val pathCode = parameters["order"]
                val body: Any? = Json.decodeFromString(receive<String>())

                when {
                    queryCode != null && searchAuth(name, user, queryCode, dbHelper) -> JWTPrincipal(credential.payload)
                    pathCode != null && searchAuth(name, user, pathCode, dbHelper) -> JWTPrincipal(credential.payload)
                    body is CreateOrderDto && user.code == body.ktiCodven -> JWTPrincipal(credential.payload)
                    else -> null
                }
            }
        }
        challenge { _, _ ->
            call.respond(
                status = HttpStatusCode.Forbidden,
                message = ServerResponse.forbidden(
                    message = "Forbidden resource"
                ) as APIResponse.Failure
            )
        }
    }
}
