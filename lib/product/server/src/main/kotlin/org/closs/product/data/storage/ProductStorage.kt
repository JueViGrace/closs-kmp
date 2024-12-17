package org.closs.product.data.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.product.CreateProductDto
import org.closs.core.shared.types.product.ProductDto
import org.closs.core.shared.types.product.UpdateProductDto
import org.closs.core.types.product.toDb
import org.closs.core.types.product.toDto

interface ProductStorage {
    suspend fun getProducts(): List<ProductDto>
    suspend fun getProductByCode(code: String): ProductDto?
    suspend fun getExistingProducts(): List<ProductDto>
    suspend fun getExistingProductByCode(code: String): ProductDto?
    suspend fun createProduct(dto: CreateProductDto, images: List<String>): ProductDto?
    suspend fun updateProduct(dto: UpdateProductDto, images: List<String>): ProductDto?
}

class DefaultProductStorage(
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : ProductStorage {
    override suspend fun getProducts(): List<ProductDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossProductQueries.findProducts()
            ).map { product ->
                product.toDto()
            }
        }
    }

    override suspend fun getProductByCode(code: String): ProductDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossProductQueries.findProductByCode(code)
            )?.toDto()
        }
    }

    override suspend fun getExistingProducts(): List<ProductDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossProductQueries.findExistingProducts()
            ).map { product ->
                product.toDto()
            }
        }
    }

    override suspend fun getExistingProductByCode(code: String): ProductDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossProductQueries.findExistingProductByCode(code)
            )?.toDto()
        }
    }

    override suspend fun createProduct(dto: CreateProductDto, images: List<String>): ProductDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.clossProductQueries
                        .insert(
                            closs_product = dto.toDb(images)
                        )
                        .executeAsOneOrNull()
                        ?.toDto()
                        ?: rollback(null)
                }
            }
        }.await()
    }

    override suspend fun updateProduct(dto: UpdateProductDto, images: List<String>): ProductDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.clossProductQueries
                        .update(
                            grupo = dto.grupo,
                            subgrupo = dto.subgrupo,
                            nombre = dto.nombre,
                            referencia = dto.referencia,
                            marca = dto.marca,
                            unidad = dto.unidad,
                            discont = dto.discont,
                            existencia = dto.existencia,
                            vta_max = dto.vtaMax,
                            vta_min = dto.vtaMin,
                            vta_minenx = dto.vtaMinEx,
                            comprometido = dto.comprometido,
                            precio1 = dto.precio1,
                            precio2 = dto.precio2,
                            precio3 = dto.precio3,
                            precio4 = dto.precio4,
                            precio5 = dto.precio5,
                            precio6 = dto.precio6,
                            precio7 = dto.precio7,
                            preventa = dto.preventa,
                            vta_solofac = dto.vtaSoloFac,
                            vta_solone = dto.vtaSoloNe,
                            codbarras = dto.codBarras,
                            detalles = dto.detalles,
                            cantbulto = dto.cantBulto,
                            costo_prom = dto.costoProm,
                            util1 = dto.util1,
                            util2 = dto.util2,
                            util3 = dto.util3,
                            fchultcomp = dto.fchUltComp,
                            qtyultcomp = dto.qtyUltComp,
                            images = images.joinToString(","),
                            code = dto.codigo
                        )
                        .executeAsOneOrNull()
                        ?.toDto()
                        ?: rollback(null)
                }
            }
        }.await()
    }
}
