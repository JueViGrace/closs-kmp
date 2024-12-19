package org.closs.salesman.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.closs.core.shared.types.search.SearchByManagerCodeDto
import org.closs.core.shared.types.search.SearchBySalesmanCodeDto
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse
import org.closs.salesman.data.handler.SalesmanHandler

fun Route.getStatisticsByManager(handler: SalesmanHandler) {
    get("/manager/{manager}") {
        val manager = call.parameters["manager"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Manager must be provided"
                )
            )

        val response = handler.getStatisticsByManager(manager)

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

fun Route.getStatisticsBySalesman(handler: SalesmanHandler) {
    get("/salesman/{code}") {
        val code = call.parameters["code"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Salesman must be provided"
                )
            )

        val response = handler.getStatisticsBySalesman(code)

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
