package org.closs.product.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import org.closs.core.shared.types.product.CreateProductDto
import org.closs.core.shared.types.product.UpdateProductDto
import org.closs.core.types.applicationResponse
import org.closs.product.data.handler.ProductHandler

fun Route.getProducts(handler: ProductHandler) {
    get {
        val response = handler.getProducts()

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

fun Route.getProductByCode(handler: ProductHandler) {
    post<ProductByIdDto> { body ->
        val response = handler.getProductByCode(body.id)

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

// todo: manage product image files uploads
// todo: validate product image files
// todo: manage deletion or insertion of new images

fun Route.createProduct(handler: ProductHandler) {
    // todo: get path for images
    post<CreateProductDto> { body ->
//                    val multipart = call.receiveMultipart()
        val response = handler.createProduct(
            dto = body,
            images = emptyList()
        )

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

fun Route.updateProduct(handler: ProductHandler) {
    put<UpdateProductDto> { dto ->
        val response = handler.updateProduct(
            dto = dto,
            images = emptyList()
        )

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
