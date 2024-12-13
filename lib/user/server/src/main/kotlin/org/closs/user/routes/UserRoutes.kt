package org.closs.user.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
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

    // Normal user auth
    authenticate(JwtAuthName.SESSION.value, strategy = AuthenticationStrategy.Required) {
        route("/users") {
            // Admin user auth
            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
                route("/admin") {
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
            }

            authenticate(JwtAuthName.USER.value, strategy = AuthenticationStrategy.Required) {
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
        }
    }
}
