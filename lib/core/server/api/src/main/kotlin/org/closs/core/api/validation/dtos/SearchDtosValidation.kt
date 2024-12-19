package org.closs.core.api.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.search.SearchByManagerCodeDto

fun RequestValidationConfig.validateSearchDtos() {
    validate<SearchByManagerCodeDto> { dto ->
        when {
            else -> ValidationResult.Valid
        }
    }
}