package org.closs.customer.routes

import io.ktor.server.routing.Route
import org.closs.customer.data.handler.CustomerHandler
import org.koin.ktor.ext.inject

fun Route.customerRoutes() {
    val handler: CustomerHandler by inject()
}
