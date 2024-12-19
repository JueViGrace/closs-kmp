package org.closs.customer.routes

import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.customer.data.handler.CustomerHandler
import org.koin.ktor.ext.inject

fun Route.customerRouter() {
    val handler: CustomerHandler by inject()

    route("/customers") {
        route("/admin") {
            createCustomer(handler)
        }

        getCustomerByCode(handler)

        getCustomersByManager(handler)

        getCustomersBySalesman(handler)
    }
}
