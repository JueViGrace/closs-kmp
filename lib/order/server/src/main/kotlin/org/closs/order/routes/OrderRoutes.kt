package org.closs.order.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.types.JwtAuthName
import org.closs.order.data.handler.OrderHandler
import org.koin.ktor.ext.inject

fun Route.orderRoutes() {
    val handler: OrderHandler by inject<OrderHandler>()

    authenticate(JwtAuthName.SESSION.value, strategy = AuthenticationStrategy.Required) {
        route("/orders") {
            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
                route("/admin") {
                    post<CreateOrderDto> {

                    }
                }
            }

            authenticate(JwtAuthName.ORDER.value, strategy = AuthenticationStrategy.Required) {

            }
        }
    }
}
