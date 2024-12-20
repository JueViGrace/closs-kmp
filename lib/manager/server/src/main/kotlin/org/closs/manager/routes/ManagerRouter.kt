package org.closs.manager.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.core.types.JwtAuthName
import org.closs.manager.data.handler.ManagerHandler
import org.koin.ktor.ext.inject

fun Route.managerRouter() {
    val handler: ManagerHandler by inject()

    authenticate(
        JwtAuthName.SESSION.value,
        JwtAuthName.MANAGER.value,
        JwtAuthName.MANAGER_CODE.value,
        strategy = AuthenticationStrategy.Required
    ) {
        route("/manager") {
            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
                route("/admin") {
                    getManagerByCode(handler)

                    createManager(handler)
                }
            }
        }

        getManagersByCode(handler)
    }
}
