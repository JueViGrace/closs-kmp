package org.closs.user.data.handler

import kotlinx.coroutines.withContext
import org.closs.core.shared.types.user.CreateUserDto
import org.closs.core.shared.types.user.UpdateLastSyncDto
import org.closs.core.shared.types.user.UserDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.user.data.storage.UserStorage
import kotlin.coroutines.CoroutineContext

// todo: check for conflicts
interface UserHandler {
    suspend fun getExistingUserById(id: String): APIResponse<UserDto?>
    suspend fun getExistingUserByUsername(username: String): APIResponse<UserDto?>
    suspend fun createUser(dto: CreateUserDto): APIResponse<UserDto?>
    suspend fun updateLastSync(dto: UpdateLastSyncDto): APIResponse<UserDto?>
    suspend fun softDeleteUser(id: String): APIResponse<String>
    suspend fun deleteUser(id: String): APIResponse<String>
}

class DefaultUserHandler(
    private val coroutineContext: CoroutineContext,
    private val store: UserStorage
) : UserHandler {
    override suspend fun getExistingUserById(id: String): APIResponse<UserDto?> {
        return withContext(coroutineContext) {
            val result = store.getExistingUserById(id)
                ?: return@withContext ServerResponse.notFound(
                    message = "User with id $id was not found"
                )

            ServerResponse.ok(data = result, message = "Processed successfully")
        }
    }

    override suspend fun getExistingUserByUsername(username: String): APIResponse<UserDto?> {
        return withContext(coroutineContext) {
            val result = store.getExistingUserByUsername(username)
                ?: return@withContext ServerResponse.notFound(
                    message = "User with id $username was not found"
                )

            ServerResponse.ok(data = result, message = "Processed successfully")
        }
    }

    override suspend fun createUser(dto: CreateUserDto): APIResponse<UserDto?> {
        return withContext(coroutineContext) {
            val result = store.createUser(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to create user, try again later"
                )

            ServerResponse.ok(data = result, message = "Processed successfully")
        }
    }

    override suspend fun updateLastSync(dto: UpdateLastSyncDto): APIResponse<UserDto?> {
        return withContext(coroutineContext) {
            val result = store.updateLastSync(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to update last sync, try again later"
                )

            ServerResponse.ok(data = result, message = "Processed successfully")
        }
    }

    override suspend fun softDeleteUser(id: String): APIResponse<String> {
        return withContext(coroutineContext) {
            val result = store.softDeleteUser(id)

            if (result != null) {
                return@withContext ServerResponse.badRequest(
                    message = "Unable to delete user, try again later"
                )
            }

            ServerResponse.ok(data = "User deleted!", message = "Processed successfully")
        }
    }

    override suspend fun deleteUser(id: String): APIResponse<String> {
        return withContext(coroutineContext) {
            val result = store.deleteUser(id)

            if (result != null) {
                return@withContext ServerResponse.badRequest(
                    message = "Unable to delete user, try again later"
                )
            }

            ServerResponse.ok(data = "User deleted!", message = "Processed successfully")
        }
    }
}
