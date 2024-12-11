package org.closs.user.di

import org.closs.user.data.handler.DefaultUserHandler
import org.closs.user.data.handler.UserHandler
import org.closs.user.data.storage.DefaultUserStorage
import org.closs.user.data.storage.UserStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun userModule(): Module = module {
    singleOf(::DefaultUserStorage) bind UserStorage::class

    singleOf(::DefaultUserHandler) bind UserHandler::class
}
