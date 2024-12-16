package org.closs.config.di

import org.closs.config.data.handler.ConfigHandler
import org.closs.config.data.handler.DefaultConfigHandler
import org.closs.config.data.storage.ConfigStore
import org.closs.config.data.storage.DefaultConfigStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun configModule(): Module = module {
    singleOf(::DefaultConfigStore) bind ConfigStore::class

    singleOf(::DefaultConfigHandler) bind ConfigHandler::class
}
