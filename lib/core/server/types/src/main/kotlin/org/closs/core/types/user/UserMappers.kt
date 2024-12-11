package org.closs.core.types.user

import org.closs.core.shared.types.user.UserDto
import org.closs.core.types.aliases.DbUser

fun DbUser.toDto(): UserDto = UserDto(
    id = id,
    username = username,
    createdAt = created_at
)
