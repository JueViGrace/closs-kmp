package org.closs.product.data.handler

import kotlinx.coroutines.withContext
import org.closs.core.shared.types.product.CreateProductDto
import org.closs.core.shared.types.product.ProductDto
import org.closs.core.shared.types.product.UpdateProductDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.product.data.storage.ProductStorage
import kotlin.coroutines.CoroutineContext

interface ProductHandler {
    suspend fun getProducts(): APIResponse<List<ProductDto>>
    suspend fun getProductByCode(code: String): APIResponse<ProductDto?>
    suspend fun getExistingProducts(): APIResponse<List<ProductDto>>
    suspend fun getExistingProductByCode(code: String): APIResponse<ProductDto?>
    suspend fun createProduct(dto: CreateProductDto, images: List<String>): APIResponse<ProductDto?>
    suspend fun updateProduct(dto: UpdateProductDto, images: List<String>): APIResponse<ProductDto?>
}

class DefaultProductHandler(
    private val coroutineContext: CoroutineContext,
    private val store: ProductStorage
) : ProductHandler {
    override suspend fun getProducts(): APIResponse<List<ProductDto>> {
        return withContext(coroutineContext) {
            val result = store.getProducts()

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    data = result,
                    message = "Products were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getProductByCode(code: String): APIResponse<ProductDto?> {
        return withContext(coroutineContext) {
            val result = store.getProductByCode(code)
                ?: return@withContext ServerResponse.notFound(
                    message = "Products with code $code was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getExistingProducts(): APIResponse<List<ProductDto>> {
        return withContext(coroutineContext) {
            val result = store.getExistingProducts()

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    data = result,
                    message = "Products were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getExistingProductByCode(code: String): APIResponse<ProductDto?> {
        return withContext(coroutineContext) {
            val result = store.getExistingProductByCode(code)
                ?: return@withContext ServerResponse.notFound(
                    message = "Products with code $code was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createProduct(
        dto: CreateProductDto,
        images: List<String>
    ): APIResponse<ProductDto?> {
        return withContext(coroutineContext) {
            val result = store.createProduct(dto, images)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to create product, try again later"
                )

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun updateProduct(
        dto: UpdateProductDto,
        images: List<String>
    ): APIResponse<ProductDto?> {
        return withContext(coroutineContext) {
            val result = store.updateProduct(dto, images = emptyList())
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to update product, try again later"
                )

            ServerResponse.accepted(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
