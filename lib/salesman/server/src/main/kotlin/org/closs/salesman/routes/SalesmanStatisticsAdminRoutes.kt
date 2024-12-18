package org.closs.salesman.routes

import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import org.closs.core.shared.types.salesman.CreateSalesmanStatisticDto
import org.closs.core.shared.types.salesman.UpdateSalesmanStatisticDto
import org.closs.salesman.data.handler.SalesmanHandler

fun Route.createSalesmanStatistics(handler: SalesmanHandler) {
    post<CreateSalesmanStatisticDto> {
    }
}

fun Route.updateSalesmanStatistics(handler: SalesmanHandler) {
    put<UpdateSalesmanStatisticDto> {
    }
}
