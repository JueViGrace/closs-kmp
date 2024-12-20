package org.closs.core.types.aliases

import org.closs.core.database.Closs_order
import org.closs.core.database.Closs_order_lines
import org.closs.core.database.FindOrderWithLines

typealias DbOrder = Closs_order
typealias OrderWithLines = FindOrderWithLines
typealias OrderLines = Closs_order_lines
