package org.closs.server.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import org.closs.core.validation.serverRequestValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        serverRequestValidation()
    }
}
