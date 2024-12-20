package org.closs.core.validation.authentication

import io.ktor.server.auth.AuthenticationConfig
import org.closs.core.database.helper.DbHelper
import org.closs.core.types.JwtAuthName
import org.closs.core.util.Jwt

fun AuthenticationConfig.serverAuthValidation(jwt: Jwt, dbHelper: DbHelper) {
    adminAuth(
        name = JwtAuthName.ADMIN,
        jwt = jwt,
        dbHelper = dbHelper
    )

    sessionAuth(
        name = JwtAuthName.SESSION,
        jwt = jwt,
        dbHelper = dbHelper
    )

    userAuth(
        name = JwtAuthName.USER_ID,
        jwt = jwt,
        dbHelper = dbHelper
    )

    managerAuth(
        name = JwtAuthName.MANAGER,
        jwt = jwt,
        dbHelper = dbHelper
    )

    managerCodeAuth(
        name = JwtAuthName.MANAGER_CODE,
        jwt = jwt,
        dbHelper = dbHelper
    )

    salesmanCodeAuth(
        name = JwtAuthName.SALESMAN_CODE,
        jwt = jwt,
        dbHelper = dbHelper
    )

    customerCodeAuth(
        name = JwtAuthName.CUSTOMER_CODE,
        jwt = jwt,
        dbHelper = dbHelper
    )

    orderCodeAuth(
        name = JwtAuthName.ORDER_CODE,
        jwt = jwt,
        dbHelper = dbHelper
    )

    documentCodeAuth(
        name = JwtAuthName.DOCUMENT_CODE,
        jwt = jwt,
        dbHelper = dbHelper
    )
}
