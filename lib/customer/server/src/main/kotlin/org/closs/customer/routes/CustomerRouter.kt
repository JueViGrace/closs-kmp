package org.closs.customer.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.core.types.JwtAuthName
import org.closs.customer.data.handler.CustomerHandler
import org.koin.ktor.ext.inject

fun Route.customerRouter() {
    val handler: CustomerHandler by inject()

    authenticate(
        JwtAuthName.SESSION.value,
        JwtAuthName.CUSTOMER_CODE.value,
        strategy = AuthenticationStrategy.Required
    ) {
        route("/customers") {
            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
                route("/admin") {
                    createCustomer(handler)
                }
            }

            getCustomerByCode(handler)

            getCustomersByManager(handler)

            getCustomersBySalesman(handler)
        }
    }
}
