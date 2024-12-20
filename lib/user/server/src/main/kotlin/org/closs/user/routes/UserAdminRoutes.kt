package org.closs.user.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.post
import org.closs.core.shared.types.user.CreateUserDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse
import org.closs.core.util.Util.validUuid
import org.closs.user.data.handler.UserHandler

fun Route.createUser(handler: UserHandler) {
    post<CreateUserDto>("/new") { dto ->
        val response = handler.createUser(dto)

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

fun Route.softDeleteUser(handler: UserHandler) {
    delete("/{id}") {
        val id = validUuid(call.parameters["id"])
            ?: return@delete call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "User id must be a valid id"
                ) as APIResponse.Failure
            )

        val response = handler.softDeleteUser(id)

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

fun Route.deleteUser(handler: UserHandler) {
    delete("/forever/{id}") {
        val id = validUuid(call.parameters["id"])
            ?: return@delete call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "User id must be a valid id"
                ) as APIResponse.Failure
            )

        val response = handler.deleteUser(id)

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
