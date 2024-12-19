package org.closs.salesman.routes

import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.salesman.data.handler.SalesmanHandler
import org.koin.ktor.ext.inject

fun Route.salesmanRouter() {
    val handler: SalesmanHandler by inject()

//    authenticate(JwtAuthName.SALESMAN_ROUTES.value, strategy = AuthenticationStrategy.Required) {
    route("/salesman") {
//            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
        route("/admin") {
            getSalesmenByManager(handler)

            getSalesmanByCode(handler)

            createSalesman(handler)

            updateSalesman(handler)
        }
//            }
        getExistingSalesmenByManager(handler)

        getExistingSalesmanByCode(handler)

        route("/statistics") {
            route("/admin") {
                createSalesmanStatistics(handler)

                updateSalesmanStatistics(handler)
            }
            getStatisticsByManager(handler)

            getStatisticsBySalesman(handler)
        }
    }
//    }
}
