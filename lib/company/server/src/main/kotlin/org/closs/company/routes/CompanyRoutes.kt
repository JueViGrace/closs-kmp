package org.closs.company.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.closs.company.data.handler.CompanyHandler
import org.closs.core.shared.types.company.CompanyByCodeDto
import org.closs.core.shared.types.company.CreateCompanyDto
import org.closs.core.shared.types.company.UpdateCompanyDto
import org.closs.core.types.JwtAuthName
import org.closs.core.types.applicationResponse
import org.koin.ktor.ext.inject

fun Route.companyRoutes() {
    val handler: CompanyHandler by inject()

    route("/company") {
        authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
            route("/admin") {
                post<CreateCompanyDto> { dto ->
                    val response = handler.createCompany(dto)

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

                put<UpdateCompanyDto> { dto ->
                    val response = handler.updateCompany(dto)

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

        post<CompanyByCodeDto> { dto ->
            val response = handler.getCompanyByCode(dto.code)

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
