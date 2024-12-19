package org.closs.core.validation.authentication

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import org.closs.core.database.helper.DbHelper
import org.closs.core.types.JwtAuthName
import org.closs.core.util.Jwt

fun AuthenticationConfig.sessionAuth(
    jwt: Jwt,
    dbHelper: DbHelper
) {
    jwt(name = JwtAuthName.SESSION.value) {
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
    }
}
