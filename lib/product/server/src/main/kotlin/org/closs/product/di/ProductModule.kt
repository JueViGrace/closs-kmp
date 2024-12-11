package org.closs.product.di

import org.closs.product.data.handler.DefaultProductHandler
import org.closs.product.data.handler.ProductHandler
import org.closs.product.data.storage.DefaultProductStorage
import org.closs.product.data.storage.ProductStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun productModule(): Module = module {
    singleOf(::DefaultProductHandler) bind ProductHandler::class

    singleOf(::DefaultProductStorage) bind ProductStorage::class
}
