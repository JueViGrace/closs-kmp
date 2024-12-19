package org.closs.core.validation.authentication

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import org.closs.core.database.helper.DbHelper
import org.closs.core.types.JwtAuthName
import org.closs.core.types.Role
import org.closs.core.util.Jwt
import org.closs.core.validation.user.auth.getUserAuthData

fun AuthenticationConfig.adminAuth(
    jwt: Jwt,
    dbHelper: DbHelper
) {
    jwt(name = JwtAuthName.ADMIN.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val user = extractId(credential)?.let { id ->
                    getUserAuthData(id, dbHelper)
                } ?: return@validateCredential null

                return@validateCredential when (user.role) {
                    Role.ADMIN -> JWTPrincipal(credential.payload)
                    else -> null
                }
            }
        }
    }
}
