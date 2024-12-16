package org.closs.config.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.config.validation.dtos.validateConfigByUserDto
import org.closs.config.validation.dtos.validateCreateConfigDto

fun RequestValidationConfig.configValidation() {
    validateCreateConfigDto()
    validateConfigByUserDto()
}
