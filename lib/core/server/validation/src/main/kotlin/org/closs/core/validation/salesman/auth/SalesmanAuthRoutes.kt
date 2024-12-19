package org.closs.core.validation.salesman.auth

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import kotlinx.serialization.json.Json
import org.closs.core.shared.types.search.SearchByManagerCodeDto
import org.closs.core.shared.types.search.SearchBySalesmanCodeDto
import org.closs.core.types.JwtAuthName
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation
import org.closs.core.util.Jwt

fun AuthenticationConfig.salesmanRoutesAuth(
    jwt: Jwt,
    userCall: suspend (String) -> UserIdValidation?,
    searchByManagerCall: suspend (UserIdValidation, String) -> Boolean,
    searchByCodeCall: suspend (UserIdValidation, String) -> Boolean,
) {
    jwt(name = JwtAuthName.SALESMAN_ROUTES.value) {
        realm = jwt.realm
        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val user = extractId(credential)?.let { id ->
                    userCall(id)
                } ?: return@validateCredential null
                val body: Any = Json.decodeFromString(receive<String>())

                return@validateCredential when {
                    user.role == Role.ADMIN -> JWTPrincipal(credential.payload)
                    body is SearchByManagerCodeDto -> {
                        if (searchByManagerCall(user, body.manager)) {
                            return@validateCredential JWTPrincipal(credential.payload)
                        }
                        null
                    }
                    body is SearchBySalesmanCodeDto -> {
                        if (searchByCodeCall(user, body.code)) {
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
