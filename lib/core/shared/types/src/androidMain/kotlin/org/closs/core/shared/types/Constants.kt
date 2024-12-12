package org.closs.core.shared.types

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

actual object Constants {
    actual const val APP_VERSION: String = "0.0.1"
    actual val currentTime: String = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).toString()
    actual const val MINIMUM_LENGTH: Int = 4

    actual const val SNACK_BAR_MESSAGE_KEY: String = "SNACK_BAR_MESSAGE_KEY"
    actual const val SEARCH_BAR_TEXT_KEY: String = "SEARCH_BAR_TEXT_KEY"
}
