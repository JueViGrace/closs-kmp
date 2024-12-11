package org.closs.core.shared.types

import kotlinx.datetime.LocalDateTime

fun Throwable.log(tag: String) =
    println(
        """
            $tag, 
            Message: ${this.message}\n
            Localized Message: ${this.localizedMessage}
        """.trimIndent()
    )

fun LocalDateTime.formatDate(): String {
    return "${this.date} ${this.time}"
}
