package org.closs.core.types.user

import org.closs.core.shared.types.user.CreateUserDto
import org.closs.core.shared.types.user.UserDto
import org.closs.core.types.Role
import org.closs.core.types.aliases.DbUser
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun CreateUserDto.toDb(): DbUser = DbUser(
    id = Uuid.random().toString(),
    username = username,
    password = password,
    codigo = code,
    role = Role.SALESMAN.name,
    ult_sinc = "",
    version = "",
    created_at = "",
    updated_at = "",
    deleted_at = "",
)

fun DbUser.toDto(): UserDto = UserDto(
    id = id,
    username = username,
    code = codigo,
    lastSync = ult_sinc,
    version = version,
    createdAt = created_at,
    updatedAt = updated_at
)
