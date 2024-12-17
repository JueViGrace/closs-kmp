package org.closs.user.data.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.user.CreateUserDto
import org.closs.core.shared.types.user.UpdateLastSyncDto
import org.closs.core.shared.types.user.UserDto
import org.closs.core.types.user.toDb
import org.closs.core.types.user.toDto

interface UserStorage {
    suspend fun getExistingUserById(id: String): UserDto?
    suspend fun getExistingUserByUsername(username: String): UserDto?
    suspend fun createUser(dto: CreateUserDto): UserDto?
    suspend fun updateLastSync(dto: UpdateLastSyncDto): UserDto?
    suspend fun softDeleteUser(id: String): UserDto?
    suspend fun deleteUser(id: String): UserDto?
}

class DefaultUserStorage(
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : UserStorage {
    override suspend fun getExistingUserById(id: String): UserDto? {
        return dbHelper.withDatabase { db ->
            dbHelper.executeOne(
                query = db.clossUserQueries.findExistingUser(id)
            )?.toDto()
        }
    }

    override suspend fun getExistingUserByUsername(username: String): UserDto? {
        return dbHelper.withDatabase { db ->
            dbHelper.executeOne(
                query = db.clossUserQueries.findExisitngByUsername(username)
            )?.toDto()
        }
    }

    override suspend fun createUser(dto: CreateUserDto): UserDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val user = db.clossUserQueries
                        .insert(
                            closs_user = dto.toDb()
                        ).executeAsOneOrNull()
                        ?: rollback(null)
                    getExistingUserById(user.id)
                }
            }
        }.await()
    }

    override suspend fun updateLastSync(dto: UpdateLastSyncDto): UserDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val user = db.clossUserQueries
                        .updateLastSync(
                            lastSync = dto.lastSync,
                            version = dto.version,
                            id = dto.id
                        ).executeAsOneOrNull()
                        ?: rollback(null)
                    getExistingUserById(user.id)
                }
            }
        }.await()
    }

    override suspend fun softDeleteUser(id: String): UserDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.clossTokenQueries.delete(id)
                    val user = db.clossUserQueries.softDelete(id).executeAsOneOrNull()?.toDto()
                    if (user != null) {
                        rollback(user)
                    }
                    null
                }
            }
        }.await()
    }

    override suspend fun deleteUser(id: String): UserDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val user = db.clossUserQueries.deleteById(id).executeAsOneOrNull()?.toDto()
                    if (user != null) {
                        rollback(user)
                    }
                    null
                }
            }
        }.await()
    }
}
