package org.closs.core.types

data class UserIdValidation(
    val isAdmin: Boolean = false,
    val userId: String,
)
