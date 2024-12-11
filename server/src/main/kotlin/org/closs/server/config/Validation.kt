package org.closs.server.config

import org.closs.core.api.validation.serverRequestValidation
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        serverRequestValidation()
    }
}
