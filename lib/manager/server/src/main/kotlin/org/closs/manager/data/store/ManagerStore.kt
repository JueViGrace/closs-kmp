package org.closs.manager.data.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.manager.CreateManagerDto
import org.closs.core.shared.types.manager.ManagerDto
import org.closs.core.types.manager.toDb
import org.closs.core.types.manager.toDto

interface ManagerStore {
    suspend fun getManagersByCode(manager: String): List<ManagerDto>
    suspend fun getManagerByCode(manager: String, code: String): ManagerDto?
    suspend fun createManager(dto: CreateManagerDto): ManagerDto?
}

class DefaultManagerStore(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : ManagerStore {
    override suspend fun getManagersByCode(manager: String): List<ManagerDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossManagersQueries.findManagersByCode(manager)
            ).map { manager -> manager.toDto() }
        }
    }

    override suspend fun getManagerByCode(manager: String, code: String): ManagerDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossManagersQueries.findManagerByCode(
                    manager = manager,
                    code = code
                )
            )?.toDto()
        }
    }

    override suspend fun createManager(dto: CreateManagerDto): ManagerDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbManager = db.clossManagersQueries.insert(
                        closs_managers = dto.toDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)
                    getManagerByCode(
                        manager = dbManager.kng_codgcia,
                        code = dbManager.kng_codcoord
                    )
                }
            }
        }.await()
    }
}
