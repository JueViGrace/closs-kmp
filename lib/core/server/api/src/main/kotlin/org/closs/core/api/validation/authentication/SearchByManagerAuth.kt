package org.closs.core.api.validation.authentication

import org.closs.core.types.Role
import org.closs.core.types.UserIdValidation

fun searchByManagerData(
    user: UserIdValidation,
    manager: String
): Boolean {
    return when {
        user.role != Role.MANAGER -> false
        user.code != manager -> false
        else -> true
    }
}
