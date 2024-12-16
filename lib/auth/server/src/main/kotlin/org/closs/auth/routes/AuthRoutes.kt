package org.closs.auth.routes

import io.ktor.http.Cookie
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.closs.auth.data.handler.AuthHandler
import org.closs.core.shared.types.auth.ForgotPasswordDto
import org.closs.core.shared.types.auth.RefreshTokenDto
import org.closs.core.shared.types.auth.SignInDto
import org.closs.core.types.applicationResponse
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val handler: AuthHandler by inject<AuthHandler>()

    route("/auth") {
        post<SignInDto>("/signIn") { dto ->
            val response = handler.signIn(dto)

            call.applicationResponse(
                response = response,
                onSuccess = { res ->
                    res.data?.refreshToken?.let { value -> call.response.cookies.append(Cookie("refreshToken", value)) }

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

        post<ForgotPasswordDto>("/forgotPassword") { dto ->
            val response = handler.forgotPassword(dto)

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

        post<RefreshTokenDto>("/refresh") { dto ->
            val response = handler.refresh(dto)

            call.applicationResponse(
                response = response,
                onSuccess = { res ->
                    res.data?.refreshToken?.let { value -> call.response.cookies.append(Cookie("refreshToken", value)) }

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
