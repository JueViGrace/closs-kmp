package org.closs.core.types.user

import org.closs.core.shared.types.user.UserDto
import org.closs.core.types.aliases.DbUser

fun UserDto.dtoToDomain(): User = User(
    id = id,
    username = username,
    createdAt = createdAt
)

fun User.domainToDb(): DbUser = DbUser(
    id = id,
    username = username,
    created_at = createdAt
)
