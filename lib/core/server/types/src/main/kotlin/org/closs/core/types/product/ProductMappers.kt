package org.closs.core.types.product

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.product.CreateProductDto
import org.closs.core.shared.types.product.ProductDto
import org.closs.core.types.aliases.DbProduct

fun DbProduct.toDto(): ProductDto = ProductDto(
    codigo = codigo,
    grupo = grupo,
    subgrupo = subgrupo,
    nombre = nombre,
    referencia = referencia,
    marca = marca,
    unidad = unidad,
    discont = discont,
    existencia = existencia,
    vtaMax = vta_max,
    vtaMin = vta_min,
    vtaMinEx = vta_minenx,
    comprometido = comprometido,
    precio1 = precio1,
    precio2 = precio2,
    precio3 = precio3,
    precio4 = precio4,
    precio5 = precio5,
    precio6 = precio6,
    precio7 = precio7,
    preventa = preventa,
    vtaSoloFac = vta_solofac,
    vtaSolOne = vta_solone,
    codBarras = codbarras,
    detalles = detalles,
    cantBulto = cantbulto,
    costoProm = costo_prom,
    util1 = util1,
    util2 = util2,
    util3 = util3,
    fchUltComp = fchultcomp,
    qtyUltComp = qtyultcomp,
    images = images.split(","),
    createdAt = created_at,
    updatedAt = updated_at,
)

fun CreateProductDto.toDb(images: List<String>): DbProduct = DbProduct(
    codigo = codigo,
    grupo = grupo,
    subgrupo = subgrupo,
    nombre = nombre,
    referencia = referencia,
    marca = marca,
    unidad = unidad,
    discont = discont,
    existencia = existencia,
    vta_max = vtaMax,
    vta_min = vtaMin,
    vta_minenx = vtaMinEx,
    comprometido = comprometido,
    precio1 = precio1,
    precio2 = precio2,
    precio3 = precio3,
    precio4 = precio4,
    precio5 = precio5,
    precio6 = precio6,
    precio7 = precio7,
    preventa = preventa,
    vta_solofac = vtaSoloFac,
    vta_solone = vtaSoloNe,
    codbarras = codBarras,
    detalles = detalles,
    cantbulto = cantBulto,
    costo_prom = costoProm,
    util1 = util1,
    util2 = util2,
    util3 = util3,
    fchultcomp = fchUltComp,
    qtyultcomp = qtyUltComp,
    images = images.joinToString(","),
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
    deleted_at = null
)
