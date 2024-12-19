package org.closs.core.validation.config

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.config.dtos.validateCreateConfigDto

fun RequestValidationConfig.configValidation() {
    validateCreateConfigDto()
}
