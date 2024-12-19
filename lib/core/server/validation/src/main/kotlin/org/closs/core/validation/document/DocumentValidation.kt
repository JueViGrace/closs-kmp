package org.closs.core.validation.document

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import org.closs.core.validation.document.dtos.validateCreateDocumentDto

fun RequestValidationConfig.documentValidation() {
    validateCreateDocumentDto()
}
