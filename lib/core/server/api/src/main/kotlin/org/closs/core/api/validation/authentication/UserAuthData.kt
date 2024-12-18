package org.closs.core.api.validation.authentication

import org.closs.core.database.helper.DbHelper
import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation

suspend fun getUserAuthData(id: String, dbHelper: DbHelper): UserIdValidation? {
    val dbUser = dbHelper.withDatabase { db ->
        executeOne(
            query = db.clossUserQueries.findExistingUser(id)
        )
    }

    if (dbUser == null) {
        return null
    }

    return UserIdValidation(
        role = Role.valueOf(dbUser.role),
        userId = dbUser.id,
        username = dbUser.username,
        code = dbUser.codigo
    )
}
