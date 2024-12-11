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
    suspend fun getProductById(id: String): ProductDto?
    suspend fun getExistingProducts(): List<ProductDto>
    suspend fun getExistingProductById(id: String): ProductDto?
    suspend fun createProduct(dto: CreateProductDto, images: List<String>): ProductDto?
    suspend fun updateProduct(dto: UpdateProductDto, images: List<String>): ProductDto?
    suspend fun softDeleteProduct(id: String): ProductDto?
    suspend fun deleteProduct(id: String): ProductDto?
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

    override suspend fun getProductById(id: String): ProductDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossProductQueries.findProduct(id)
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

    override suspend fun getExistingProductById(id: String): ProductDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossProductQueries.findExistingProduct(id)
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
                    val product = db.clossProductQueries
                        .update(
                            images = images.joinToString(","),
                            id = dto.id
                        )
                        .executeAsOneOrNull()
                        ?.toDto()
                    if (product == null) {
                        rollback(null)
                    }
                    product
                }
            }
        }.await()
    }

    override suspend fun softDeleteProduct(id: String): ProductDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val product = db.clossProductQueries.softDelete(id).executeAsOneOrNull()?.toDto()
                    if (product != null) {
                        rollback(product)
                    }
                    null
                }
            }
        }.await()
    }

    override suspend fun deleteProduct(id: String): ProductDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val product = db.clossProductQueries.delete(id).executeAsOneOrNull()?.toDto()
                    if (product != null) {
                        rollback(product)
                    }
                    null
                }
            }
        }.await()
    }
}
