package org.closs.config.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.config.CreateConfigDto

fun RequestValidationConfig.validateCreateConfigDto() {
    validate<CreateConfigDto> { dto ->
        when {
            dto.idConfig.isBlank() -> ValidationResult.Invalid("Id config is blank")
            dto.clase.isBlank() -> ValidationResult.Invalid("Clase is blank")
            dto.tipo.isBlank() -> ValidationResult.Invalid("Tipo is blank")
            dto.valNum.isNaN() -> ValidationResult.Invalid("Val num is NaN")
            dto.valSino < 0 -> ValidationResult.Invalid("Val sino is negative")
            dto.valTxt.isBlank() -> ValidationResult.Invalid("Val txt is blank")
            dto.lenTxt < 0 -> ValidationResult.Invalid("Len txt is negative")
            dto.valFch.isBlank() -> ValidationResult.Invalid("Val fch is blank")
            dto.activa < 0 -> ValidationResult.Invalid("Activa is negative")
            dto.etiq.isBlank() -> ValidationResult.Invalid("Etiq is blank")
            dto.ttip.isBlank() -> ValidationResult.Invalid("Ttip is blank")
            dto.username.isBlank() -> ValidationResult.Invalid("Username is blank")
            else -> ValidationResult.Valid
        }
    }
}