package org.closs.document.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.closs.core.shared.types.document.CreateDocumentDto
import org.closs.core.types.applicationResponse
import org.closs.document.data.handler.DocumentHandler

fun Route.createDocument(handler: DocumentHandler) {
    post<CreateDocumentDto> { dto ->
        val response = handler.createDocument(dto)

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
