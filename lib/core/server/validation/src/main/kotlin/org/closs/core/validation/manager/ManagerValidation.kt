package org.closs.core.validation.manager

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.manager.dtos.validateCreateManagerDto

fun RequestValidationConfig.managerValidation() {
    validateCreateManagerDto()
}
