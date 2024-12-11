package org.closs.core.types.auth

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.auth.SignUpDto
import org.closs.core.types.Role
import org.closs.core.types.aliases.DbUser
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun SignUpDto.toDb(): DbUser = DbUser(
    id = Uuid.random().toString(),
    username = username,
    password = password,
    role = Role.USER.value,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
    deleted_at = null
)
