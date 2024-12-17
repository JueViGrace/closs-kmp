package org.closs.customer.di

import org.closs.customer.data.handler.CustomerHandler
import org.closs.customer.data.handler.DefaultCustomerHandler
import org.closs.customer.data.store.CustomerStore
import org.closs.customer.data.store.DefaultCustomerStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun customerModule(): Module = module {
    singleOf(::DefaultCustomerStore) bind CustomerStore::class

    singleOf(::DefaultCustomerHandler) bind CustomerHandler::class
}
