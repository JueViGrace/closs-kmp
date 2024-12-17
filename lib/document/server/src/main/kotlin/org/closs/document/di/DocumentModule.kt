package org.closs.document.di

import org.closs.document.data.handler.DefaultDocumentHandler
import org.closs.document.data.handler.DocumentHandler
import org.closs.document.data.store.DefaultDocumentStore
import org.closs.document.data.store.DocumentStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun documentModule(): Module = module {
    singleOf(::DefaultDocumentStore) bind DocumentStore::class

    singleOf(::DefaultDocumentHandler) bind DocumentHandler::class
}
