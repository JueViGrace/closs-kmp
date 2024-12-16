package org.closs.config.data.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.config.ConfigDto
import org.closs.core.shared.types.config.CreateConfigDto
import org.closs.core.types.config.dbConfigToDto
import org.closs.core.types.config.toDbConfig

interface ConfigStore {
    suspend fun getConfigsByUsername(username: String): List<ConfigDto>
    suspend fun createConfig(dto: CreateConfigDto): ConfigDto?
}

class DefaultConfigStore(
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : ConfigStore {
    override suspend fun getConfigsByUsername(username: String): List<ConfigDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossConfigQueries.findUserConfigByUsername(
                    username = username
                )
            ).map { config -> config.dbConfigToDto() }
        }
    }

    override suspend fun createConfig(dto: CreateConfigDto): ConfigDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.clossConfigQueries
                        .insert(
                            closs_config = dto.toDbConfig()
                        )
                        .executeAsOneOrNull()
                        ?.dbConfigToDto()
                        ?: rollback(null)
                }
            }
        }.await()
    }
}
