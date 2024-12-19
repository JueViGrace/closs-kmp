package org.closs.core.validation.authentication

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import kotlinx.serialization.json.Json
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.search.SearchByManagerCodeDto
import org.closs.core.types.JwtAuthName
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation
import org.closs.core.util.Jwt
import org.closs.core.validation.user.auth.getUserAuthData

fun AuthenticationConfig.managerAuth(
    jwt: Jwt,
    dbHelper: DbHelper,
    searchManagersCall: suspend (UserIdValidation, String) -> Boolean,
) {
    jwt(name = JwtAuthName.ADMIN.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val user: UserIdValidation = extractId(credential)?.let { id ->
                    getUserAuthData(id, dbHelper)
                } ?: return@validateCredential null

                val body: Any = Json.decodeFromString(receive<String>())

                return@validateCredential when {
                    user.role == Role.ADMIN -> JWTPrincipal(credential.payload)
                    body is SearchByManagerCodeDto -> {
                        if (searchManagersCall(user, body.manager)) {
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
