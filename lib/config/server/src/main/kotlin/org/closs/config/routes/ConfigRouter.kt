package org.closs.config.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.config.data.handler.ConfigHandler
import org.closs.core.types.JwtAuthName
import org.koin.ktor.ext.inject

fun Route.configRouter() {
    val handler: ConfigHandler by inject()

    authenticate(
        JwtAuthName.SESSION.value,
        JwtAuthName.USER_ID.value,
        strategy = AuthenticationStrategy.Required
    ) {
        route("/configs") {
            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
                route("/admin") {
                    createConfig(handler)
                }
            }

            getConfigsByUsername(handler)
        }
    }
}
