package org.closs.salesman.di

import org.closs.salesman.data.handler.DefaultSalesmanHandler
import org.closs.salesman.data.handler.SalesmanHandler
import org.closs.salesman.data.store.DefaultSalesmanStore
import org.closs.salesman.data.store.SalesmanStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun salesmanModule(): Module = module {
    singleOf(::DefaultSalesmanStore) bind SalesmanStore::class

    singleOf(::DefaultSalesmanHandler) bind SalesmanHandler::class
}
