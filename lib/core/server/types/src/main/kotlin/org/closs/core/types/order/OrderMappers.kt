package org.closs.core.types.order

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.order.CreateOrderDetailsDto
import org.closs.core.shared.types.order.CreateOrderDto
import org.closs.core.shared.types.order.OrderDetailsDto
import org.closs.core.shared.types.order.OrderDto
import org.closs.core.types.aliases.DbOrder
import org.closs.core.types.aliases.OrderLines
import org.closs.core.types.aliases.OrderWithLines

fun DbOrder.dbOrderToDto(): OrderDto = OrderDto(
    ktiNdoc = kti_ndoc,
    ktiTdoc = kti_tdoc,
    ktiCodcli = kti_codcli,
    ktiNombrecli = kti_nombrecli,
    ktiCodven = kti_codven,
    ktiDocsol = kti_docsol,
    ktiCondicion = kti_condicion,
    ktiTipprec = kti_tipprec.toInt(),
    ktiTotneto = kti_totneto,
    ktiStatus = kti_status.toInt(),
    ktiNroped = kti_nroped,
    ktiFchdoc = kti_fchdoc,
    ktiNegesp = kti_negesp.toInt(),
    kePedstatus = ke_pedstatus.toInt(),
    dolarFlete = dolarflete.toInt(),
    complemento = complemento.toInt(),
    nroComplemento = nro_complemento,
    createdAt = created_at,
    updatedAt = updated_at,
    details = emptyList(),
)

// todo: this should be done in other way

fun List<OrderWithLines>.orderLinesToDto(): OrderDto? {
    val group: Map<OrderDto, List<OrderWithLines>> = this.groupBy { row ->
        OrderDto(
            ktiNdoc = row.kti_ndoc,
            ktiTdoc = row.kti_tdoc,
            ktiCodcli = row.kti_codcli,
            ktiNombrecli = row.kti_nombrecli,
            ktiCodven = row.kti_codven,
            ktiDocsol = row.kti_docsol,
            ktiCondicion = row.kti_condicion,
            ktiTipprec = row.kti_tipprec.toInt(),
            ktiTotneto = row.kti_totneto,
            ktiStatus = row.kti_status.toInt(),
            ktiNroped = row.kti_nroped,
            ktiFchdoc = row.kti_fchdoc,
            ktiNegesp = row.kti_negesp.toInt(),
            kePedstatus = row.ke_pedstatus.toInt(),
            dolarFlete = row.dolarflete.toInt(),
            complemento = row.complemento.toInt(),
            nroComplemento = row.nro_complemento,
            createdAt = row.created_at,
            updatedAt = row.updated_at,
            details = emptyList(),
        )
    }

    val dto: OrderDto? = group.map { (order: OrderDto, rows: List<OrderWithLines>) ->
        return@map OrderDto(
            ktiNdoc = order.ktiNdoc,
            ktiTdoc = order.ktiTdoc,
            ktiCodcli = order.ktiCodcli,
            ktiNombrecli = order.ktiNombrecli,
            ktiCodven = order.ktiCodven,
            ktiDocsol = order.ktiDocsol,
            ktiCondicion = order.ktiCondicion,
            ktiTipprec = order.ktiTipprec,
            ktiTotneto = order.ktiTotneto,
            ktiStatus = order.ktiStatus,
            ktiNroped = order.ktiNroped,
            ktiFchdoc = order.ktiFchdoc,
            ktiNegesp = order.ktiNegesp,
            kePedstatus = order.kePedstatus,
            dolarFlete = order.dolarFlete,
            complemento = order.complemento,
            nroComplemento = order.nroComplemento,
            createdAt = order.createdAt,
            updatedAt = order.updatedAt,
            details = rows.orderLinesToDetails()
        )
    }.firstOrNull()

    return dto
}

fun List<OrderWithLines>.orderLinesToDetails(): List<OrderDetailsDto> {
    this.firstOrNull { row -> row.kti_ndoc_ == null } ?: return emptyList()

    val details: List<OrderDetailsDto> = this.map { row ->
        return@map OrderDetailsDto(
            ktiTdoc = row.kti_tdoc_ ?: "",
            ktiNdoc = row.kti_ndoc_ ?: "",
            ktiTipprec = row.kti_tipprec_ ?: "",
            kmvCodart = row.kmv_codart ?: "",
            kmvNombre = row.kmv_nombre ?: "",
            kmvCant = row.kmv_cant?.toInt() ?: 0,
            kmvArtprec = row.kmv_artprec ?: 0.0,
            kmvStot = row.kmv_stot ?: 0.0,
            kmvDctolin = row.kmv_dctolin ?: 0.0,
            createdAt = row.created_at_ ?: "",
            updatedAt = row.updated_at_ ?: "",
            images = row.images?.split(",") ?: emptyList(),
        )
    }

    return details
}

fun CreateOrderDto.dtoToDbOrder(): DbOrder = DbOrder(
    kti_ndoc = ktiNdoc,
    kti_tdoc = ktiTdoc,
    kti_codcli = ktiCodcli,
    kti_nombrecli = ktiNombrecli,
    kti_codven = ktiCodven,
    kti_docsol = ktiDocsol,
    kti_condicion = ktiCondicion,
    kti_tipprec = ktiTipprec.toLong(),
    kti_totneto = ktiTotneto,
    kti_status = ktiStatus.toLong(),
    kti_nroped = ktiNroped,
    kti_fchdoc = ktiFchdoc,
    kti_negesp = ktiNegesp.toLong(),
    ke_pedstatus = kePedstatus.toLong(),
    dolarflete = dolarFlete.toLong(),
    complemento = complemento.toLong(),
    nro_complemento = nroComplemento,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
)

fun CreateOrderDetailsDto.toDbDetails(): OrderLines = OrderLines(
    kti_tdoc = ktiTdoc,
    kti_ndoc = ktiNdoc,
    kti_tipprec = ktiTipprec,
    kmv_codart = kmvCodart,
    kmv_nombre = kmvNombre,
    kmv_cant = kmvCant.toLong(),
    kmv_artprec = kmvArtprec,
    kmv_stot = kmvStot,
    kmv_dctolin = kmvDctolin,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
)
