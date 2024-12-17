package org.closs.core.types.customer

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.customer.CreateCustomerDto
import org.closs.core.shared.types.customer.CustomerDto
import org.closs.core.types.aliases.DbCustomer

fun DbCustomer.toDto(): CustomerDto = CustomerDto(
    codigo = codigo,
    nombre = nombre,
    email = email,
    direccion = direccion,
    telefonos = telefonos,
    perscont = perscont,
    vendedor = vendedor,
    contribspecial = contribspecial.toInt(),
    status = status.toInt(),
    sector = sector,
    subsector = subsector,
    precio = precio.toInt(),
    kneActiva = kne_activa.toInt(),
    noemifac = noemifac.toInt(),
    noeminota = noeminota.toInt(),
    fchultvta = fchultvta,
    mtoultvta = mtoultvta,
    prcdpagdia = prcdpagdia,
    promdiasp = promdiasp,
    riesgocrd = riesgocrd,
    cantdocs = cantdocs,
    totmtodocs = totmtodocs,
    prommtodoc = prommtodoc,
    diasultvta = diasultvta,
    promdiasvta = promdiasvta,
    limcred = limcred,
    fchcrea = fchcrea,
    dolarflete = dolarflete.toInt(),
    nodolarflete = nodolarflete.toInt(),
    createdAt = created_at,
    updatedAt = updated_at
)

fun CreateCustomerDto.toDb(): DbCustomer = DbCustomer(
    codigo = codigo,
    nombre = nombre,
    email = email,
    direccion = direccion,
    telefonos = telefonos,
    perscont = perscont,
    vendedor = vendedor,
    contribspecial = contribspecial.toLong(),
    status = status.toLong(),
    sector = sector,
    subsector = subsector,
    precio = precio.toLong(),
    kne_activa = kneActiva.toLong(),
    noemifac = noemifac.toLong(),
    noeminota = noeminota.toLong(),
    fchultvta = fchultvta,
    mtoultvta = mtoultvta,
    prcdpagdia = prcdpagdia,
    promdiasp = promdiasp,
    riesgocrd = riesgocrd,
    cantdocs = cantdocs,
    totmtodocs = totmtodocs,
    prommtodoc = prommtodoc,
    diasultvta = diasultvta,
    promdiasvta = promdiasvta,
    limcred = limcred,
    fchcrea = fchcrea,
    dolarflete = dolarflete.toLong(),
    nodolarflete = nodolarflete.toLong(),
    created_at = Constants.currentTime,
    updated_at =Constants.currentTime
)
