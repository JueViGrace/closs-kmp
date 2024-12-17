package org.closs.document.validation.dtos

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.closs.core.shared.types.document.CreateDocumentDto
import sun.security.util.KeyUtil.validate

fun RequestValidationConfig.validateCreateDocumentDto() {
    validate<CreateDocumentDto> { dto ->
        when {
            dto.agencia.isBlank() -> ValidationResult.Invalid("Agencia cannot be empty")
            dto.tipodoc.isBlank() -> ValidationResult.Invalid("Tipodoc cannot be empty")
            dto.documento.isBlank() -> ValidationResult.Invalid("Documento cannot be empty")
            dto.tipodocv.isBlank() -> ValidationResult.Invalid("Tipodocv cannot be empty")
            dto.codcliente.isBlank() -> ValidationResult.Invalid("Codcliente cannot be empty")
            dto.nombrecli.isBlank() -> ValidationResult.Invalid("Nombrecli cannot be empty")
            dto.contribesp < 0 -> ValidationResult.Invalid("Contribesp cannot be negative")
            dto.rutaParme < 0 -> ValidationResult.Invalid("Rutaparme cannot be negative")
            dto.tipoprecio < 0 -> ValidationResult.Invalid("Tipoprecio cannot be negative")
            dto.emision.isBlank() -> ValidationResult.Invalid("Emision cannot be empty")
            dto.recepcion.isBlank() -> ValidationResult.Invalid("Recepcion cannot be empty")
            dto.vence.isBlank() -> ValidationResult.Invalid("Vence cannot be empty")
            dto.diascred.isNaN() -> ValidationResult.Invalid("Diascred cannot be empty")
            dto.estatusdoc < 0 -> ValidationResult.Invalid("Estatusdoc cannot be negative")
            dto.dtotneto.isNaN() -> ValidationResult.Invalid("Dtotneto cannot be empty")
            dto.dtotimpuest.isNaN() -> ValidationResult.Invalid("Dtotimpuest cannot be empty")
            dto.dtotalfinal.isNaN() -> ValidationResult.Invalid("Dtotalfinal cannot be empty")
            dto.dtotpagos.isNaN() -> ValidationResult.Invalid("Dtotpagos cannot be empty")
            dto.dtotdescuen.isNaN() -> ValidationResult.Invalid("Dtotdescuen cannot be empty")
            dto.dFlete.isNaN() -> ValidationResult.Invalid("Dflete cannot be empty")
            dto.dtotdev.isNaN() -> ValidationResult.Invalid("Dtotdev cannot be empty")
            dto.dvndmtototal.isNaN() -> ValidationResult.Invalid("Dvndmtototal cannot be empty")
            dto.dretencion.isNaN() -> ValidationResult.Invalid("Dretencion cannot be empty")
            dto.dretencioniva.isNaN() -> ValidationResult.Invalid("Dretencioniva cannot be empty")
            dto.vendedor.isBlank() -> ValidationResult.Invalid("Vendedor cannot be empty")
            dto.codcoord.isBlank() -> ValidationResult.Invalid("Codcoord cannot be empty")
            dto.aceptadev < 0 -> ValidationResult.Invalid("Aceptadev cannot be negative")
            dto.ktiNegesp < 0 -> ValidationResult.Invalid("KtiNegesp cannot be negative")
            dto.bsiva.isNaN() -> ValidationResult.Invalid("Bsiva cannot be empty")
            dto.bsflete.isNaN() -> ValidationResult.Invalid("Bsflete cannot be empty")
            dto.bsretencion.isNaN() -> ValidationResult.Invalid("Bsretencion cannot be empty")
            dto.bsretencioniva.isNaN() -> ValidationResult.Invalid("Bsretencioniva cannot be empty")
            dto.tasadoc.isNaN() -> ValidationResult.Invalid("Tasadoc cannot be empty")
            dto.mtodcto.isNaN() -> ValidationResult.Invalid("Mtodcto cannot be empty")
            dto.fchvencedcto.isBlank() -> ValidationResult.Invalid("Fchvencedcto cannot be empty")
            dto.tienedcto < 0 -> ValidationResult.Invalid("Tienedcto cannot be negative")
            dto.cbsret.isNaN() -> ValidationResult.Invalid("Cbsret cannot be empty")
            dto.cdret.isNaN() -> ValidationResult.Invalid("Cdret cannot be empty")
            dto.cbsretiva.isNaN() -> ValidationResult.Invalid("Cbsretiva cannot be empty")
            dto.cdretiva.isNaN() -> ValidationResult.Invalid("Cdretiva cannot be empty")
            dto.cbsrparme.isNaN() -> ValidationResult.Invalid("Cbsrparme cannot be empty")
            dto.cdrparme.isNaN() -> ValidationResult.Invalid("Cdrparme cannot be empty")
            dto.cbsretflete.isNaN() -> ValidationResult.Invalid("Cbsretflete cannot be empty")
            dto.cdretflete.isNaN() -> ValidationResult.Invalid("Cdretflete cannot be empty")
            dto.bsmtoiva.isNaN() -> ValidationResult.Invalid("Bsmtoiva cannot be empty")
            dto.bsmtofte.isNaN() -> ValidationResult.Invalid("Bsmtofte cannot be empty")
            dto.retmunMto.isNaN() -> ValidationResult.Invalid("Retmunmto cannot be empty")
            dto.dolarflete < 0 -> ValidationResult.Invalid("Dolarflete cannot be negative")
            dto.bsretflete.isNaN() -> ValidationResult.Invalid("Bsretflete cannot be empty")
            dto.dretflete.isNaN() -> ValidationResult.Invalid("Dretflete cannot be empty")
            dto.dretmunMto.isNaN() -> ValidationResult.Invalid("Dretmunmto cannot be empty")
            dto.retivaoblig < 0 -> ValidationResult.Invalid("Retivaoblig cannot be negative")
            dto.edoentrega < 0 -> ValidationResult.Invalid("Edoentrega cannot be negative")
            dto.dmtoiva.isNaN() -> ValidationResult.Invalid("Dmtoiva cannot be empty")
            dto.prcdctoaplic.isNaN() -> ValidationResult.Invalid("Prcdctoaplic cannot be empty")
            dto.montodctodol.isNaN() -> ValidationResult.Invalid("Montodctodol cannot be empty")
            dto.montodctobs.isNaN() -> ValidationResult.Invalid("Montodctobs cannot be empty")
            else -> ValidationResult.Valid
        }
    }
}
