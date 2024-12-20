package org.closs.company.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.closs.company.data.handler.CompanyHandler
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse

fun Route.getCompanyByCode(handler: CompanyHandler) {
    get("/{code}") {
        val code = call.parameters["code"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Company code is required"
                )
            )
        val response = handler.getCompanyByCode(code)

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
