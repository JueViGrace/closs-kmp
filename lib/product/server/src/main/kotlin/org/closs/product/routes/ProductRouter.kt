package org.closs.product.routes

import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.closs.product.data.handler.ProductHandler
import org.koin.ktor.ext.inject

fun Route.productRouter() {
    val handler by inject<ProductHandler>()

//    authenticate(JwtAuthName.SESSION.value, strategy = AuthenticationStrategy.Required) {
    route("/products") {
        // Product admin routes
//            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
        route("/admin") {
            getProducts(handler)

            getProductByCode(handler)

            createProduct(handler)

            updateProduct(handler)
        }
    }

    getExistingProducts(handler)

    getExistingProductByCode(handler)
//        }
//    }
}
