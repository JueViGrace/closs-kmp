package org.closs.manager.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.closs.core.shared.types.manager.CreateManagerDto
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse
import org.closs.manager.data.handler.ManagerHandler

fun Route.getManagerByCode(handler: ManagerHandler) {
    get("/{manager}/{code}") {
        val manager = call.parameters["manager"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Manager code is required"
                )
            )
        val code = call.parameters["code"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Coordinator code is required"
                )
            )
        val response = handler.getManagerByCode(manager, code)

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

fun Route.createManager(handler: ManagerHandler) {
    post<CreateManagerDto> { dto ->
        val response = handler.createManager(dto)

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
