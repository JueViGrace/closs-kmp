package org.closs.user.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import org.closs.core.shared.types.user.UpdateLastSyncDto
import org.closs.core.shared.types.user.UserByIdDto
import org.closs.core.shared.types.user.UserByUsernameDto
import org.closs.core.types.applicationResponse
import org.closs.user.data.handler.UserHandler

fun Route.getExistingUserByIdRoute(handler: UserHandler) {
    post<UserByIdDto> { body ->
        val response = handler.getExistingUserById(body.id)

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
    post<UserByUsernameDto> { body ->
        val response = handler.getExistingUserByUsername(body.username)

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
