package org.closs.core.api.di

import org.closs.auth.di.authModule
import org.closs.order.di.orderModule
import org.closs.product.di.productModule
import org.closs.user.di.userModule
import org.koin.core.module.Module

fun serverModule(): List<Module> = listOf(
    coroutinesModule(),
    databaseModule(),
    utilModule(),
    authModule(),
    userModule(),
    productModule(),
    orderModule(),
)
