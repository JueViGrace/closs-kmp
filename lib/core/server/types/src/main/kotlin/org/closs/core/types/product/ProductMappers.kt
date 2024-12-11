package org.closs.core.types.product

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.product.CreateProductDto
import org.closs.core.shared.types.product.ProductDto
import org.closs.core.types.aliases.DbProduct
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun DbProduct.toDto(): ProductDto = ProductDto(
    id = id,
    images = images.split(","),
)

@OptIn(ExperimentalUuidApi::class)
fun CreateProductDto.toDb(images: List<String>): DbProduct = DbProduct(
    id = Uuid.random().toString(),
    images = images.joinToString(","),
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
    deleted_at = null
)
