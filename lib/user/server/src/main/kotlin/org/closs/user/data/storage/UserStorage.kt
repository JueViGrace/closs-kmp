package org.closs.user.data.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.user.UpdateUserDto
import org.closs.core.shared.types.user.UserDto
import org.closs.core.types.user.toDto

interface UserStorage {
    suspend fun getUsers(): List<UserDto>
    suspend fun getUserById(id: String): UserDto?
    suspend fun getExistingUserById(id: String): UserDto?
    suspend fun updateUser(dto: UpdateUserDto): UserDto?
    suspend fun softDeleteUser(id: String): UserDto?
    suspend fun deleteUser(id: String): UserDto?
}

class DefaultUserStorage(
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : UserStorage {
    override suspend fun getUsers(): List<UserDto> {
        return dbHelper.withDatabase { db ->
            dbHelper.executeList(
                query = db.clossUserQueries.findUsers()
            ).map { user ->
                user.toDto()
            }
        }
    }

    override suspend fun getUserById(id: String): UserDto? {
        return dbHelper.withDatabase { db ->
            dbHelper.executeOne(
                query = db.clossUserQueries.findUser(id)
            )?.toDto()
        }
    }

    override suspend fun getExistingUserById(id: String): UserDto? {
        return dbHelper.withDatabase { db ->
            dbHelper.executeOne(
                query = db.clossUserQueries.findExistingUser(id)
            )?.toDto()
        }
    }

    override suspend fun updateUser(dto: UpdateUserDto): UserDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val user = db.clossUserQueries
                        .update(
                            id = dto.id
                        ).executeAsOneOrNull()
                        ?.toDto()
                    if (user == null) {
                        rollback(null)
                    }
                    user
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
