package org.closs.customer.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse
import org.closs.customer.data.handler.CustomerHandler

fun Route.getCustomerByCode(handler: CustomerHandler) {
    get("/{customer}") {
        val code = call.parameters["customer"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Customer code is required"
                )
            )
        val response = handler.getCustomerByCode(code)

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

fun Route.getCustomersByManager(handler: CustomerHandler) {
    get {
        val manager = call.request.queryParameters["manager"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Manager code is required"
                )
            )

        val response = handler.getCustomersByManager(manager)

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

fun Route.getCustomersBySalesman(handler: CustomerHandler) {
    get {
        val salesman = call.request.queryParameters["salesman"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Manager code is required"
                )
            )

        val response = handler.getCustomersBySalesman(salesman)

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
