package org.closs.core.types.manager

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.manager.CreateManagerDto
import org.closs.core.shared.types.manager.ManagerDto
import org.closs.core.types.aliases.DbManager

fun DbManager.toDto(): ManagerDto = ManagerDto(
    kngCodgcia = kng_codgcia,
    kngCodcoord = kng_codcoord,
    createdAt = created_at,
    updatedAt = updated_at
)

fun CreateManagerDto.toDb(): DbManager = DbManager(
    kng_codgcia = kngCodgcia,
    kng_codcoord = kngCodcoord,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
    deleted_at = null
)
