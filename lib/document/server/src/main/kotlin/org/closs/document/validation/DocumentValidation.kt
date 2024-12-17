package org.closs.document.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.document.validation.dtos.validateCreateDocumentDto
import org.closs.document.validation.dtos.validateDocumentByCodeDto

fun RequestValidationConfig.documentValidation() {
    validateCreateDocumentDto()
    validateDocumentByCodeDto()
}
