package org.closs.product.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse
import org.closs.product.data.handler.ProductHandler

fun Route.getExistingProducts(handler: ProductHandler) {
    get {
        val response = handler.getExistingProducts()

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

fun Route.getExistingProductByCode(handler: ProductHandler) {
    get("/{code}") {
        val code = call.parameters["code"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Product code must be provided"
                )
            )
        val response = handler.getExistingProductByCode(code)

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
