package org.closs.manager.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse
import org.closs.manager.data.handler.ManagerHandler

fun Route.getManagersByCode(handler: ManagerHandler) {
    get("/{manager}") {
        val manager = call.parameters["manager"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Manager code is required"
                )
            )

        val response = handler.getManagersByCode(manager)

        call.applicationResponse(
            response = response,
            onFailure = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description
                    ),
                    message = res
                )
            },
            onSuccess = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description
                    ),
                    message = res
                )
            }
        )
    }
}
