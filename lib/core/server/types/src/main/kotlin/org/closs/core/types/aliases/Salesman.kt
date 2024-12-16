package org.closs.core.types.aliases

import org.closs.core.database.FindExistingSalesmanByCode
import org.closs.core.database.FindSalesmanByCode
import org.closs.core.database.FindSalesmanByManager

typealias SalesmanByManager = FindSalesmanByManager
typealias SalesmanByCode = FindSalesmanByCode
typealias ExistingSalesmanByCode = FindExistingSalesmanByCode
