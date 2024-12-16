package org.closs.auth.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.auth.validation.dtos.validateSignInDto

fun RequestValidationConfig.authValidation() {
    validateSignInDto()
}

