package org.closs.core.types.salesman

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.salesman.CreateSalesmanDto
import org.closs.core.shared.types.salesman.SalesmanDto
import org.closs.core.types.aliases.DbSalesman
import org.closs.core.types.aliases.ExistingSalesmanByCode
import org.closs.core.types.aliases.ExistingSalesmenByManager
import org.closs.core.types.aliases.SalesmanByCode
import org.closs.core.types.aliases.SalesmenByManager

fun SalesmenByManager.salesmanByManagerToDto(): SalesmanDto {
    return SalesmanDto(
        codigo = codigo,
        nombre = nombre,
        username = username ?: "",
        email = email,
        telefono = telefono,
        telefonos = telefonos,
        supervpor = supervpor,
        sector = sector ?: "",
        subsector = subsector ?: "",
        ultSinc = ult_sinc ?: "",
        version = version ?: "",
        createdAt = created_at,
        updatedAt = updated_at
    )
}

fun ExistingSalesmenByManager.salesmanByManagerToDto(): SalesmanDto {
    return SalesmanDto(
        codigo = codigo,
        nombre = nombre,
        username = username ?: "",
        email = email,
        telefono = telefono,
        telefonos = telefonos,
        supervpor = supervpor,
        sector = sector ?: "",
        subsector = subsector ?: "",
        ultSinc = ult_sinc ?: "",
        version = version ?: "",
        createdAt = created_at,
        updatedAt = updated_at
    )
}

fun SalesmanByCode.salesmanByCodeToDto(): SalesmanDto {
    return SalesmanDto(
        codigo = codigo,
        nombre = nombre,
        username = username ?: "",
        email = email,
        telefono = telefono,
        telefonos = telefonos,
        supervpor = supervpor,
        sector = sector ?: "",
        subsector = subsector ?: "",
        ultSinc = ult_sinc ?: "",
        version = version ?: "",
        createdAt = created_at,
        updatedAt = updated_at
    )
}

fun ExistingSalesmanByCode.existingSalesmanByCodeToDto(): SalesmanDto {
    return SalesmanDto(
        codigo = codigo,
        nombre = nombre,
        username = username ?: "",
        email = email,
        telefono = telefono,
        telefonos = telefonos,
        supervpor = supervpor,
        sector = sector ?: "",
        subsector = subsector ?: "",
        ultSinc = ult_sinc ?: "",
        version = version ?: "",
        createdAt = created_at,
        updatedAt = updated_at
    )
}

fun CreateSalesmanDto.createDtoToDbSalesman(): DbSalesman = DbSalesman(
    codigo = codigo,
    nombre = nombre,
    email = email,
    telefono = telefono,
    telefonos = telefonos,
    status = status.toLong(),
    supervpor = supervpor,
    sector = sector,
    subcodigo = subcodigo,
    nivgcial = nivgcial.toLong(),
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime
)
