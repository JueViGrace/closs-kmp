package org.closs.auth.routes

import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.auth.data.handler.AuthHandler
import org.koin.ktor.ext.inject

fun Route.authRouter() {
    val handler: AuthHandler by inject<AuthHandler>()

    route("/auth") {
        signIn(handler)

        forgotPassword(handler)

        refresh(handler)
    }
}
