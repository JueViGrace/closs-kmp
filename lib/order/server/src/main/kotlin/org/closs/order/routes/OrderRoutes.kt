package org.closs.order.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.closs.core.shared.types.order.OrderByIdDto
import org.closs.core.shared.types.order.OrderByUserDto
import org.closs.core.shared.types.order.OrdersByUserDto
import org.closs.core.types.JwtAuthName
import org.closs.core.types.applicationResponse
import org.closs.order.data.handler.OrderHandler
import org.koin.ktor.ext.inject

fun Route.orderRoutes() {
    val handler: OrderHandler by inject<OrderHandler>()

    authenticate(JwtAuthName.SESSION.value, strategy = AuthenticationStrategy.Required) {
        route("/orders") {
            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
                route("/admin") {
                    get("/all") {
                        val response = handler.getOrders()

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

                    post<OrderByIdDto> { dto ->
                        val response = handler.getOrderWithLines(dto.id)

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
            }

            authenticate(JwtAuthName.ORDER.value, strategy = AuthenticationStrategy.Required) {
                post<OrdersByUserDto>("/all") { dto ->
                    val response = handler.getOrdersByUser(dto.userId)

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

                post<OrderByUserDto> { dto ->
                    val response = handler.getOrderByUserWithLines(dto.orderId, dto.userId)

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
        }
    }
}
