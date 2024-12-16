package org.closs.company.di

import org.closs.company.data.handler.CompanyHandler
import org.closs.company.data.handler.DefaultCompanyHandler
import org.closs.company.data.storage.CompanyStore
import org.closs.company.data.storage.DefaultCompanyStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun companyModule(): Module = module {
    singleOf(::DefaultCompanyStore) bind CompanyStore::class

    singleOf(::DefaultCompanyHandler) bind CompanyHandler::class
}
