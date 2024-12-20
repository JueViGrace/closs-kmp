package org.closs.core.util

import org.closs.core.types.Role
import java.util.regex.Pattern
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object Util {
    fun verifyEmail(string: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        )

        return pattern.matcher(string).matches()
    }

    @OptIn(ExperimentalUuidApi::class)
    fun validUuid(id: String?): String? {
        return id?.let { value ->
            try {
                Uuid.parse(value).toHexString()
            } catch (e: IllegalStateException) {
                println(e.message)
                null
            }
        }
    }

    fun verifyUserRole(role: String): Role {
        return when (val value = Role.valueOf(role)) {
            Role.CUSTOMER -> value
            Role.SALESMAN -> value
            Role.MANAGER -> value
            Role.ADMIN -> value
            else -> Role.UNIDENTIFIED
        }
    }
}
