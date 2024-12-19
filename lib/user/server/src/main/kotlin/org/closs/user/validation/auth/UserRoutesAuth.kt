package org.closs.user.validation.auth

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import kotlinx.serialization.json.Json
import org.closs.core.shared.types.user.UpdateLastSyncDto
import org.closs.core.shared.types.user.UserByIdDto
import org.closs.core.shared.types.user.UserByUsernameDto
import org.closs.core.types.JwtAuthName
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation
import org.closs.core.util.Jwt

fun AuthenticationConfig.userRoutesAuth(
    jwt: Jwt,
    userCall: suspend (String) -> UserIdValidation?
) {
    jwt(name = JwtAuthName.USER_ROUTES.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val tokenId = extractId(credential) ?: return@validateCredential null

                val user = userCall(tokenId) ?: return@validateCredential null

                if (user.role == Role.ADMIN) {
                    return@validateCredential JWTPrincipal(credential.payload)
                }

                when (val body: Any = Json.decodeFromString(receive<String>())) {
                    is UserByIdDto -> {
                        if (user.userId == body.id) {
                            return@validateCredential JWTPrincipal(credential.payload)
                        }
                    }
                    is UpdateLastSyncDto -> {
                        if (body.id == user.userId) {
                            return@validateCredential JWTPrincipal(credential.payload)
                        }
                    }
                    is UserByUsernameDto -> {
                        if (user.username == body.username) {
                            return@validateCredential JWTPrincipal(credential.payload)
                        }
                    }
                    else -> return@validateCredential null
                }
                null
            }
        }
    }
}
