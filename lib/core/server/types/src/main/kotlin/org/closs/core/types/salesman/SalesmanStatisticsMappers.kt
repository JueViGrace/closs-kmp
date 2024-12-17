package org.closs.core.types.salesman

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.salesman.CreateSalesmanStatisticDto
import org.closs.core.shared.types.salesman.SalesmanStatisticsDto
import org.closs.core.types.aliases.DbSalesmanStatistics

fun DbSalesmanStatistics.toDto(): SalesmanStatisticsDto = SalesmanStatisticsDto(
    codcoord = codcoord,
    nomcoord = nomcoord,
    vendedor = vendedor,
    nombrevend = nombrevend,
    cntpedidos = cntpedidos.toInt(),
    mtopedidos = mtopedidos,
    cntfacturas = cntfacturas.toInt(),
    mtofacturas = mtofacturas,
    metavend = metavend,
    prcmeta = prcmeta,
    cntclientes = cntclientes.toInt(),
    clivisit = clivisit.toInt(),
    prcvisitas = prcvisitas,
    lomMontovtas = lom_montovtas,
    lomPrcvtas = lom_prcvtas,
    lomPrcvisit = lom_prcvisit,
    rlomMontovtas = rlom_montovtas,
    rlomPrcvtas = rlom_prcvtas,
    rlomPrcvisit = rlom_prcvisit,
    fechaEstad = fecha_estad,
    ppgdolTotneto = ppgdol_totneto,
    devdolTotneto = devdol_totneto,
    defdolTotneto = defdol_totneto,
    totdolcob = totdolcob,
    cntrecl = cntrecl.toInt(),
    mtorecl = mtorecl,
    createdAt = created_at,
    updatedAt = updated_at,
)

fun CreateSalesmanStatisticDto.toDb(): DbSalesmanStatistics = DbSalesmanStatistics(
    codcoord = codcoord,
    nomcoord = nomcoord,
    vendedor = vendedor,
    nombrevend = nombrevend,
    cntpedidos = cntpedidos.toLong(),
    mtopedidos = mtopedidos,
    cntfacturas = cntfacturas.toLong(),
    mtofacturas = mtofacturas,
    metavend = metavend,
    prcmeta = prcmeta,
    cntclientes = cntclientes.toLong(),
    clivisit = clivisit.toLong(),
    prcvisitas = prcvisitas,
    lom_montovtas = lomMontovtas,
    lom_prcvtas = lomPrcvtas,
    lom_prcvisit = lomPrcvisit,
    rlom_montovtas = rlomMontovtas,
    rlom_prcvtas = rlomPrcvtas,
    rlom_prcvisit = rlomPrcvisit,
    fecha_estad = fechaEstad,
    ppgdol_totneto = ppgdolTotneto,
    devdol_totneto = devdolTotneto,
    defdol_totneto = defdolTotneto,
    totdolcob = totdolcob,
    cntrecl = cntrecl.toLong(),
    mtorecl = mtorecl,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime
)
