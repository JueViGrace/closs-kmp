package org.closs.core.util

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
}
