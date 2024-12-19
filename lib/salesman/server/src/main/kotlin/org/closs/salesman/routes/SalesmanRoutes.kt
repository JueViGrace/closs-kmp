package org.closs.salesman.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse
import org.closs.salesman.data.handler.SalesmanHandler
import org.koin.ktor.ext.get

fun Route.getExistingSalesmenByManager(handler: SalesmanHandler) {
    get("/manager/{manager}") {
        val manager = call.parameters["manager"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Manager must be provided"
                )
            )

        val response = handler.getExistingSalesmenByManager(manager)

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

fun Route.getExistingSalesmanByCode(handler: SalesmanHandler) {
    get {
        val code = call.parameters["code"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Salesman must be provided"
                )
            )

        val response = handler.getExistingSalesmenByManager(code)

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
