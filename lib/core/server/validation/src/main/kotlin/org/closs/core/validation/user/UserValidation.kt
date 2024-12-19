package org.closs.core.validation.user

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.user.dtos.validateCreateUserDto
import org.closs.core.validation.user.dtos.validateUpdateLastSyncDto

fun RequestValidationConfig.userValidation() {
    validateCreateUserDto()
    validateUpdateLastSyncDto()
}
