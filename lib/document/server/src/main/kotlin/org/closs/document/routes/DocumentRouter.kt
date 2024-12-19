package org.closs.document.routes

import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.document.data.handler.DocumentHandler
import org.koin.ktor.ext.inject

fun Route.documentRouter() {
    val handler: DocumentHandler by inject()

//    authenticate(JwtAuthName.DOCUMENT_ROUTES.value, strategy = AuthenticationStrategy.Required) {
    route("/document") {
//            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
        route("/admin") {
            createDocument(handler)
        }
//            }

        getDocument(handler)

        getDocumentWithLines(handler)

        getDocumentsByManager(handler)

        getDocumentsBySalesman(handler)

        getDocumentsByCustomer(handler)
    }
//    }
}
