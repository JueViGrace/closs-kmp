package org.closs.document.routes

import io.ktor.server.routing.Route
import org.closs.document.data.handler.DocumentHandler
import org.koin.ktor.ext.inject

fun Route.documentRoutes() {
    val handler: DocumentHandler by inject()
}
