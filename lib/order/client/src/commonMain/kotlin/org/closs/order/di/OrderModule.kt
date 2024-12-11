package org.closs.order.di

import org.closs.order.data.repository.DefaultOrderRepository
import org.closs.order.data.repository.OrderRepository
import org.closs.order.presentation.viewmodel.OrderDetailsViewModel
import org.closs.order.presentation.viewmodel.OrdersListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun orderModule(): Module = module {
    singleOf(::DefaultOrderRepository) bind OrderRepository::class

    viewModelOf(::OrdersListViewModel)

    viewModelOf(::OrderDetailsViewModel)
}
