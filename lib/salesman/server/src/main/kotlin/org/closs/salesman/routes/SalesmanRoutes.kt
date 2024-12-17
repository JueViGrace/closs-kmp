package org.closs.salesman.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.closs.core.shared.types.salesman.CreateSalesmanDto
import org.closs.core.shared.types.salesman.CreateSalesmanStatisticDto
import org.closs.core.shared.types.salesman.UpdateSalesmanDto
import org.closs.core.shared.types.salesman.UpdateSalesmanStatisticDto
import org.closs.core.types.JwtAuthName
import org.closs.salesman.data.handler.SalesmanHandler
import org.koin.ktor.ext.inject

fun Route.salesmanRoutes() {
    val handler: SalesmanHandler by inject()

    route("/salesman") {
        authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
            route("/admin") {
                post<CreateSalesmanDto> {
                }

                put<UpdateSalesmanDto> {
                }

                post<CreateSalesmanStatisticDto> {
                }

                put<UpdateSalesmanStatisticDto> {
                }
            }
        }
    }
}
