package org.closs.core.api.di

import org.closs.auth.di.authModule
import org.closs.company.di.companyModule
import org.closs.config.di.configModule
import org.closs.customer.di.customerModule
import org.closs.document.di.documentModule
import org.closs.manager.di.managerModule
import org.closs.order.di.orderModule
import org.closs.product.di.productModule
import org.closs.salesman.di.salesmanModule
import org.closs.user.di.userModule
import org.koin.core.module.Module

fun serverModule(): List<Module> = listOf(
    coroutinesModule(),
    databaseModule(),
    utilModule(),
    authModule(),
    companyModule(),
    configModule(),
    userModule(),
    managerModule(),
    salesmanModule(),
    customerModule(),
    productModule(),
    orderModule(),
    documentModule(),
)
