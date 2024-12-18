package org.closs.salesman.validation.auth

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import kotlinx.serialization.json.Json
import org.closs.core.shared.types.search.SearchByManagerCodeDto
import org.closs.core.shared.types.search.SearchBySalesmanCodeDto
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation
import org.closs.core.util.Jwt

fun AuthenticationConfig.salesmanRoutesAuth(
    name: String,
    jwt: Jwt,
    userCall: suspend (String) -> UserIdValidation?,
    searchByManagerCall: suspend (UserIdValidation, String) -> Boolean,
    searchByCodeCall: suspend (UserIdValidation, String) -> Boolean,
) {
    jwt(name = name) {
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
                    is SearchByManagerCodeDto -> {
                        searchByManagerCall(user, body.manager)
                    }
                    is SearchBySalesmanCodeDto -> {
                        searchByCodeCall(user, body.code)
                    }
                }

                JWTPrincipal(credential.payload)
            }
        }
    }
}
