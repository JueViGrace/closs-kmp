package org.closs.company.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.closs.company.data.handler.CompanyHandler
import org.closs.core.types.applicationResponse

fun Route.getCompanyByCode(handler: CompanyHandler) {
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
