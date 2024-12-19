package org.closs.core.validation.auth

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.auth.dtos.validateSignInDto

fun RequestValidationConfig.authValidation() {
    validateSignInDto()
}

