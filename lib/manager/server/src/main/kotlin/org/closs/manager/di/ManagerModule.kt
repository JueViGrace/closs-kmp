package org.closs.manager.di

import org.closs.manager.data.handler.DefaultManagerHandler
import org.closs.manager.data.handler.ManagerHandler
import org.closs.manager.data.store.DefaultManagerStore
import org.closs.manager.data.store.ManagerStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun managerModule(): Module = module {
    singleOf(::DefaultManagerStore) bind ManagerStore::class

    singleOf(::DefaultManagerHandler) bind ManagerHandler::class
}
