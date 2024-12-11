package org.closs.order.di

import org.closs.order.data.handler.DefaultOrderHandler
import org.closs.order.data.handler.OrderHandler
import org.closs.order.data.storage.DefaultOrderStorage
import org.closs.order.data.storage.OrderStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun orderModule(): Module = module {
    singleOf(::DefaultOrderStorage) bind OrderStorage::class

    singleOf(::DefaultOrderHandler) bind OrderHandler::class
}
