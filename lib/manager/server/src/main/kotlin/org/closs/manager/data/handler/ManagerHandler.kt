package org.closs.manager.data.handler

import kotlinx.coroutines.withContext
import org.closs.core.shared.types.manager.CreateManagerDto
import org.closs.core.shared.types.manager.ManagerDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.manager.data.store.ManagerStore
import kotlin.coroutines.CoroutineContext

interface ManagerHandler {
    suspend fun getManagersByCode(manager: String): APIResponse<List<ManagerDto>>
    suspend fun getManagerByCode(manager: String, code: String): APIResponse<ManagerDto?>
    suspend fun createManager(dto: CreateManagerDto): APIResponse<ManagerDto?>
}

class DefaultManagerHandler(
    private val storage: ManagerStore,
    private val coroutineContext: CoroutineContext
) : ManagerHandler {
    override suspend fun getManagersByCode(manager: String): APIResponse<List<ManagerDto>> {
        return withContext(coroutineContext) {
            val result = storage.getManagersByCode(manager)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getManagerByCode(manager: String, code: String): APIResponse<ManagerDto?> {
        return withContext(coroutineContext) {
            val result = storage.getManagerByCode(
                manager = manager,
                code = code
            )
                ?: return@withContext ServerResponse.notFound(
                    message = "Manager with id $manager and code $code was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createManager(dto: CreateManagerDto): APIResponse<ManagerDto?> {
        return withContext(coroutineContext) {
            val result = storage.createManager(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to create manager, try again"
                )

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
