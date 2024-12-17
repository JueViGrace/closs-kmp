package org.closs.user.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.closs.core.shared.types.user.CreateUserDto
import org.closs.core.shared.types.user.UpdateLastSyncDto
import org.closs.core.shared.types.user.UserByIdDto
import org.closs.core.shared.types.user.UserByUsernameDto
import org.closs.core.types.JwtAuthName
import org.closs.core.types.applicationResponse
import org.closs.user.data.handler.UserHandler
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val handler: UserHandler by inject<UserHandler>()
    route("/users") {
        authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
            route("/admin") {
                createUserRoute(handler)

                softDeleteUserRoute(handler)

                deleteUserRoute(handler)
            }
        }

        authenticate(JwtAuthName.USER_ROUTES.value, strategy = AuthenticationStrategy.Required) {
            getExistingUserByIdRoute(handler)

            getExistingUserByUsernameRoute(handler)

            updateLastSyncRoute(handler)
        }
    }
}

fun Route.createUserRoute(handler: UserHandler) {
    post<CreateUserDto> { dto ->
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

fun Route.softDeleteUserRoute(handler: UserHandler) {
    delete {
        val body = call.receive<UserByIdDto>()
        val response = handler.softDeleteUser(body.id)

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

fun Route.deleteUserRoute(handler: UserHandler) {
    delete("/forever") {
        val body = call.receive<UserByIdDto>()
        val response = handler.deleteUser(body.id)

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
