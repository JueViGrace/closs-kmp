package org.closs.manager.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.closs.core.shared.types.manager.CreateManagerDto
import org.closs.core.types.JwtAuthName
import org.closs.manager.data.handler.ManagerHandler
import org.koin.ktor.ext.inject

fun Route.managerRoutes() {
    val handler: ManagerHandler by inject()

    route("/manager") {
        authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
            route("/admin") {
                post<CreateManagerDto> {

                }
            }
        }
    }
}
