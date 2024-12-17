package org.closs.manager.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.manager.validation.dtos.validateCreateManagerDto
import org.closs.manager.validation.dtos.validateManagerByCodeDto
import org.closs.manager.validation.dtos.validateManagersByCodeDto

fun RequestValidationConfig.managerValidation() {
    validateCreateManagerDto()
    validateManagerByCodeDto()
    validateManagersByCodeDto()
}
