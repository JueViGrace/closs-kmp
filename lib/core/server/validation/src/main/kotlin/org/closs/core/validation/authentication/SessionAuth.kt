package org.closs.core.validation.authentication

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import org.closs.core.database.helper.DbHelper
import org.closs.core.types.APIResponse
import org.closs.core.types.JwtAuthName
import org.closs.core.types.ServerResponse
import org.closs.core.util.Jwt

fun AuthenticationConfig.sessionAuth(
    name: JwtAuthName,
    jwt: Jwt,
    dbHelper: DbHelper
) {
    jwt(name = name.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                extractId(credential)?.let { id ->
                    dbHelper.withDatabase { db ->
                        executeOne(db.clossTokenQueries.findTokenById(id))
                    }
                } ?: return@validateCredential null
                JWTPrincipal(credential.payload)
            }
        }
        challenge { _, _ ->
            call.request.headers["Authorization"]?.let { token ->
                dbHelper.withDatabase { db ->
                    db.transaction {
                        db.clossTokenQueries.deleteByToken(
                            token.split(" ")[1]
                        )
                    }
                }
            }
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ServerResponse.unauthorized(
                    message = "Token is invalid"
                ) as APIResponse.Failure
            )
        }
    }
}
