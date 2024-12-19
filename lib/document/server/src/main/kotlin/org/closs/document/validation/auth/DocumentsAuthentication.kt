package org.closs.document.validation.auth

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import kotlinx.serialization.json.Json
import org.closs.core.types.JwtAuthName
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation
import org.closs.core.util.Jwt

fun AuthenticationConfig.documentsAuth(
    jwt: Jwt,
    userCall: suspend (String) -> UserIdValidation?,

) {
    jwt(name = JwtAuthName.ORDER_ROUTES.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val user: UserIdValidation = extractId(credential)?.let { id ->
                    userCall(id)
                } ?: return@validateCredential null

                val body: Any = Json.decodeFromString(receive<String>())

                return@validateCredential when {
                    user.role == Role.ADMIN -> JWTPrincipal(credential.payload)

                    else -> null
                }
            }
        }
    }
}
