package org.closs.user.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import org.closs.core.shared.types.user.UpdateLastSyncDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse
import org.closs.core.util.Util.validUuid
import org.closs.user.data.handler.UserHandler

fun Route.getExistingUserByIdRoute(handler: UserHandler) {
    get("/{id}") {
        val id = validUuid(call.parameters["id"])
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "User id must be a valid id"
                ) as APIResponse.Failure
            )

        val response = handler.getExistingUserById(id)

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

fun Route.getExistingUserByUsernameRoute(handler: UserHandler) {
    get {
        val username = call.request.queryParameters["username"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Username must be provided"
                )
            )
        val response = handler.getExistingUserByUsername(username)

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

fun Route.updateLastSyncRoute(handler: UserHandler) {
    patch<UpdateLastSyncDto> { body ->
        val response = handler.updateLastSync(body)

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
