package org.closs.core.api.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.closs.auth.routes.authRoutes
import org.closs.company.routes.companyRoutes
import org.closs.config.routes.configRoutes
import org.closs.customer.routes.customerRoutes
import org.closs.document.routes.documentRoutes
import org.closs.manager.routes.managerRoutes
import org.closs.order.routes.orderRouter
import org.closs.product.routes.productRouter
import org.closs.salesman.routes.salesmanRouter
import org.closs.user.routes.userRouter

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
    companyRoutes()
    configRoutes()
    userRouter()
    managerRoutes()
    salesmanRouter()
    customerRoutes()
    productRouter()
    orderRouter()
    documentRoutes()
}

fun Route.webRoutes() {
    get {
        call.respond(
            status = HttpStatusCode.OK,
            message = "Root"
        )
    }
}
