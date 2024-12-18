package org.closs.core.types.aliases

import org.closs.core.database.Closs_salesman
import org.closs.core.database.FindExistingSalesmanByCode
import org.closs.core.database.FindExistingSalesmenByManager
import org.closs.core.database.FindSalesmanByCode
import org.closs.core.database.FindSalesmenByManager

typealias DbSalesman = Closs_salesman
typealias SalesmenByManager = FindSalesmenByManager
typealias ExistingSalesmenByManager = FindExistingSalesmenByManager
typealias SalesmanByCode = FindSalesmanByCode
typealias ExistingSalesmanByCode = FindExistingSalesmanByCode
