package org.closs.core.validation.authentication

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import org.closs.core.database.helper.DbHelper
import org.closs.core.types.APIResponse
import org.closs.core.types.JwtAuthName
import org.closs.core.types.Role
import org.closs.core.types.ServerResponse
import org.closs.core.util.Jwt
import org.closs.core.validation.authentication.AuthenticationData.getUserId

fun AuthenticationConfig.adminAuth(
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

                if (user.role == Role.ADMIN) {
                    JWTPrincipal(credential.payload)
                }
                null
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
