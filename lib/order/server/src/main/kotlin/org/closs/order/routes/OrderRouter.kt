package org.closs.order.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.core.types.JwtAuthName
import org.closs.order.data.handler.OrderHandler
import org.koin.ktor.ext.inject

fun Route.orderRouter() {
    val handler: OrderHandler by inject<OrderHandler>()

    authenticate(JwtAuthName.ORDER_ROUTES.value, strategy = AuthenticationStrategy.Required) {
        route("/orders") {
            getOrder(handler)

            getOrderWithLines(handler)

            getAllOrdersByManager(handler)

            getOrdersByManager(handler)

            getAllOrdersBySalesman(handler)

            getOrdersBySalesman(handler)

            getAllOrdersByCustomer(handler)

            getOrdersByCustomer(handler)

            createOrder(handler)
        }
    }
}
