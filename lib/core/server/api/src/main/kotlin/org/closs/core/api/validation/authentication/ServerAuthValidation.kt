package org.closs.core.api.validation.authentication

import io.ktor.server.auth.AuthenticationConfig
import org.closs.core.database.helper.DbHelper
import org.closs.core.types.JwtAuthName
import org.closs.core.util.Jwt
import org.closs.order.validation.auth.createOrderData
import org.closs.order.validation.auth.ordersAuth
import org.closs.order.validation.auth.searchByOrderData
import org.closs.salesman.validation.auth.salesmanRoutesAuth
import org.closs.user.validation.auth.userRoutesAuth

fun AuthenticationConfig.serverAuthValidation(jwt: Jwt, dbHelper: DbHelper) {
    sessionAuth(
        name = JwtAuthName.SESSION.value,
        jwt = jwt,
        dbHelper = dbHelper
    )

    adminAuth(
        name = JwtAuthName.ADMIN.value,
        jwt = jwt,
        dbHelper = dbHelper
    )

    userRoutesAuth(
        name = JwtAuthName.USER_ROUTES.value,
        jwt = jwt,
        userCall = { id ->
            getUserAuthData(id, dbHelper)
        }
    )

    salesmanRoutesAuth(
        name = JwtAuthName.SALESMAN_ROUTES.value,
        jwt = jwt,
        userCall = { id ->
            getUserAuthData(id, dbHelper)
        },
        searchByManagerCall = { user, manager ->
            searchByManagerData(user, manager)
        },
        searchByCodeCall = { user, code ->
            searchBySalesmanData(user, code, dbHelper)
        },
    )

    ordersAuth(
        name = JwtAuthName.ORDER_ROUTES.value,
        jwt = jwt,
        userCall = { id ->
            getUserAuthData(id, dbHelper)
        },
        searchBySalesmanCall = { user, code ->
            searchBySalesmanData(user, code, dbHelper)
        },
        searchByManagerCall = { user, manager ->
            searchByManagerData(user, manager)
        },
        searchByCustomerCall = { user, code ->
            searchByCustomerData(user, code, dbHelper)
        },
        searchByIdCall = { user, orderCode ->
            searchByOrderData(user, orderCode, dbHelper)
        },
        createOrderCall = { user, dto ->
            createOrderData(user, dto, dbHelper)
        }
    )
}
