package org.closs.order.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.closs.core.types.FilterOptions
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.types.ServerResponse
import org.closs.core.types.applicationResponse
import org.closs.order.data.handler.OrderHandler

fun Route.getOrder(handler: OrderHandler) {
    get("/{document}") {
        val document = call.parameters["document"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Document code is required"
                )
            )
        val response = handler.getOrder(document)

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

fun Route.getAllOrdersByManager(handler: OrderHandler) {
    get {
        val manager = call.request.queryParameters["manager"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Manager code must be provided"
                )
            )
        val response = handler.getAllOrdersByManager(manager)

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

fun Route.getOrdersByManager(handler: OrderHandler) {
    get {
        val manager = call.request.queryParameters["manager"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Manager code must be provided"
                )
            )
        val filter = call.request.queryParameters["filter"]?.let { value ->
            when (val option = FilterOptions.valueOf(value)) {
                FilterOptions.All -> option
                FilterOptions.Month -> option
                else -> null
            }
        } ?: FilterOptions.Month

        val response = when (filter) {
            FilterOptions.All -> handler.getAllOrdersByManager(manager)
            FilterOptions.Month -> handler.getOrdersByManager(manager)
        }

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

fun Route.getOrdersBySalesman(handler: OrderHandler) {
    get {
        val salesman = call.request.queryParameters["salesman"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Salesman code must be provided"
                )
            )
        val filter = call.request.queryParameters["filter"]?.let { value ->
            when (val option = FilterOptions.valueOf(value)) {
                FilterOptions.All -> option
                FilterOptions.Month -> option
                else -> null
            }
        } ?: FilterOptions.Month

        val response = when (filter) {
            FilterOptions.All -> handler.getAllOrdersBySalesman(salesman)
            FilterOptions.Month -> handler.getOrdersBySalesman(salesman)
        }

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

fun Route.getOrdersByCustomer(handler: OrderHandler) {
    get {
        val customer = call.request.queryParameters["customer"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Salesman code must be provided"
                )
            )
        val filter = call.request.queryParameters["filter"]?.let { value ->
            when (val option = FilterOptions.valueOf(value)) {
                FilterOptions.All -> option
                FilterOptions.Month -> option
                else -> null
            }
        } ?: FilterOptions.Month

        val response = when (filter) {
            FilterOptions.All -> handler.getAllOrdersByCustomer(customer)
            FilterOptions.Month -> handler.getAllOrdersByCustomer(customer)
        }

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

fun Route.createOrder(handler: OrderHandler) {
    post<CreateOrderDto> { dto ->
        val response = handler.createOrder(dto)

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
