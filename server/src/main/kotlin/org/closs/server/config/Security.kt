package org.closs.server.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import org.closs.core.validation.authentication.serverAuthValidation
import org.closs.core.database.helper.DbHelper
import org.closs.core.util.Jwt
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    // todo: idk if this should be here
    val dbHelper: DbHelper by inject<DbHelper>()
    val jwt: Jwt by inject<Jwt>(
        parameters = {
            parametersOf(
                environment.config.property("ktor.jwt.secret").getString(),
                environment.config.property("ktor.jwt.issuer").getString(),
                environment.config.property("ktor.jwt.audience").getString(),
                environment.config.property("ktor.jwt.realm").getString(),
            )
        }
    )

    install(Authentication) {
        serverAuthValidation(jwt = jwt, dbHelper = dbHelper)
    }
}
