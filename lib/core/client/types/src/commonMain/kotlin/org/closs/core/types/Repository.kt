package org.closs.core.types

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface Repository {
    val coroutineContext: CoroutineContext
    val scope: CoroutineScope
}
