package org.closs.server.config

import org.closs.core.api.routes.serverRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRoutes() {
    routing {
        serverRoutes()
    }
}
