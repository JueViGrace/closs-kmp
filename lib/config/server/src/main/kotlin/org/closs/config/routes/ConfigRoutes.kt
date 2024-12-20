package org.closs.config.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.closs.config.data.handler.ConfigHandler
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse

fun Route.getConfigsByUsername(handler: ConfigHandler) {
    get("/{user}") {
        val username = call.parameters["user"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Username is required"
                )
            )
        val response = handler.getConfigsByUsername(username)

        call.applicationResponse(
            response = response,
            onSuccess = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description
                    ),
                    message = res
                )
            },
            onFailure = { res ->
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
