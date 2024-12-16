package org.closs.core.types.company

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.company.CompanyDto
import org.closs.core.shared.types.company.CreateCompanyDto
import org.closs.core.types.aliases.DbCompany

fun DbCompany.dbCompanyToDto(): CompanyDto = CompanyDto(
    code = ked_codigo,
    name = ked_nombre,
    status = ked_status.toInt(),
    domain = ked_enlace,
    agency = ked_agen,
    createdAt = created_at,
    updatedAt = updated_at,
)

fun CreateCompanyDto.dtoToDbCompany(): DbCompany = DbCompany(
    ked_codigo = code,
    ked_nombre = name,
    ked_status = status.toLong(),
    ked_enlace = domain,
    ked_agen = agency,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
    deleted_at = null
)
