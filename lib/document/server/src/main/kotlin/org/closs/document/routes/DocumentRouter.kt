package org.closs.document.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.core.types.JwtAuthName
import org.closs.document.data.handler.DocumentHandler
import org.koin.ktor.ext.inject

fun Route.documentRouter() {
    val handler: DocumentHandler by inject()

    authenticate(
        JwtAuthName.SESSION.value,
        JwtAuthName.DOCUMENT_CODE.value,
        strategy = AuthenticationStrategy.Required
    ) {
        route("/document") {
            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
                route("/admin") {
                    createDocument(handler)
                }
            }

            getDocument(handler)

            getDocumentsByManager(handler)

            getDocumentsBySalesman(handler)

            getDocumentsByCustomer(handler)
        }
    }
}
