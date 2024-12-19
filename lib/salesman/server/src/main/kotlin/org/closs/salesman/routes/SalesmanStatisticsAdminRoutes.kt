package org.closs.salesman.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import org.closs.core.shared.types.salesman.statistics.CreateSalesmanStatisticDto
import org.closs.core.shared.types.salesman.statistics.UpdateSalesmanStatisticDto
import org.closs.core.types.applicationResponse
import org.closs.salesman.data.handler.SalesmanHandler

fun Route.createSalesmanStatistics(handler: SalesmanHandler) {
    post<CreateSalesmanStatisticDto> { dto ->
        val response = handler.createSalesmanStatistics(dto)

        call.applicationResponse(
            response = response,
            onFailure = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description,
                    ),
                    message = res
                )
            },
            onSuccess = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description,
                    ),
                    message = res
                )
            }
        )
    }
}

fun Route.updateSalesmanStatistics(handler: SalesmanHandler) {
    put<UpdateSalesmanStatisticDto> { dto ->
        val response = handler.updateSalesmanStatistics(dto)

        call.applicationResponse(
            response = response,
            onFailure = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description,
                    ),
                    message = res
                )
            },
            onSuccess = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description,
                    ),
                    message = res
                )
            }
        )
    }
}
