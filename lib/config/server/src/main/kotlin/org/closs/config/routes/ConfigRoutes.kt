package org.closs.config.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.closs.config.data.handler.ConfigHandler
import org.closs.core.shared.types.config.ConfigByUserDto
import org.closs.core.shared.types.config.CreateConfigDto
import org.closs.core.types.JwtAuthName
import org.closs.core.types.applicationResponse
import org.koin.ktor.ext.inject

fun Route.configRoutes() {
    val handler: ConfigHandler by inject()

    authenticate(JwtAuthName.SESSION.value, strategy = AuthenticationStrategy.Required) {
        route("/configs") {
            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
                route("/admin") {
                    post<CreateConfigDto> { dto ->
                        val response = handler.getConfigsByUsername(dto.username)

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

            post<ConfigByUserDto> { dto ->
                val response = handler.getConfigsByUsername(dto.username)

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
