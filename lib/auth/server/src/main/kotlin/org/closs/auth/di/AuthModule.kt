package org.closs.auth.di

import org.closs.auth.data.handler.AuthHandler
import org.closs.auth.data.handler.DefaultAuthHandler
import org.closs.auth.data.storage.AuthStore
import org.closs.auth.data.storage.DefaultAuthStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun authModule(): Module = module {
    singleOf(::DefaultAuthStore) bind AuthStore::class

    singleOf(::DefaultAuthHandler) bind AuthHandler::class
}
