package org.closs.server.config

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.closs.core.api.routes.serverRoutes

fun Application.configureRoutes() {
    routing {
        serverRoutes()
    }
}
