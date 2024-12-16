package org.closs.user.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.user.validation.dtos.validateCreateUserDto
import org.closs.user.validation.dtos.validateUpdateLastSyncDto
import org.closs.user.validation.dtos.validateUserByIdDto
import org.closs.user.validation.dtos.validateUserByUsernameDto

fun RequestValidationConfig.userValidation() {
    validateUserByIdDto()
    validateUserByUsernameDto()
    validateCreateUserDto()
    validateUpdateLastSyncDto()
}
