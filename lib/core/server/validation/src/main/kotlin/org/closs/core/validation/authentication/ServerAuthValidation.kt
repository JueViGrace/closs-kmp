package org.closs.core.validation.authentication

import io.ktor.server.auth.AuthenticationConfig
import org.closs.core.database.helper.DbHelper
import org.closs.core.util.Jwt
import org.closs.order.validation.auth.createOrderData
import org.closs.order.validation.auth.ordersAuth
import org.closs.order.validation.auth.searchByOrderData
import org.closs.salesman.validation.auth.salesmanRoutesAuth
import org.closs.user.validation.auth.userRoutesAuth

fun AuthenticationConfig.serverAuthValidation(jwt: Jwt, dbHelper: DbHelper) {

}
