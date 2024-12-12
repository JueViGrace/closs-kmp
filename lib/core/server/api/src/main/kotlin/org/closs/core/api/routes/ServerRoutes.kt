package org.closs.core.api.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.closs.auth.routes.authRoutes
import org.closs.order.routes.orderRoutes
import org.closs.product.routes.productRoutes
import org.closs.user.routes.userRoutes

fun Routing.serverRoutes() {
    route("/api") {
        apiRoutes()
    }
    webRoutes()
}

fun Route.apiRoutes() {
    get("/health") {
        call.respond(
            status = HttpStatusCode.OK,
            message = "Health is ok!"
        )
    }

    authRoutes()
    userRoutes()
    productRoutes()
    orderRoutes()
}

fun Route.webRoutes() {
    get {
        call.respond(
            status = HttpStatusCode.OK,
            message = "Root"
        )
    }
}
