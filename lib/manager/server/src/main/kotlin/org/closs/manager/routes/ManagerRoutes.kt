package org.closs.manager.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.closs.core.shared.types.search.SearchByManagerCodeDto
import org.closs.core.types.applicationResponse
import org.closs.manager.data.handler.ManagerHandler

fun Route.getManagersByCode(handler: ManagerHandler) {
    post<SearchByManagerCodeDto> { dto ->
        val response = handler.getManagersByCode(dto.manager)

        call.applicationResponse(
            response = response,
            onFailure = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description
                    ),
                    message = res
                )
            },
            onSuccess = { res ->
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
