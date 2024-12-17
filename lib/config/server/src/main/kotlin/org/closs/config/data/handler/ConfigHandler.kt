package org.closs.config.data.handler

import kotlinx.coroutines.withContext
import org.closs.config.data.storage.ConfigStore
import org.closs.core.shared.types.config.ConfigDto
import org.closs.core.shared.types.config.CreateConfigDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import kotlin.coroutines.CoroutineContext

interface ConfigHandler {
    suspend fun getConfigsByUsername(username: String): APIResponse<List<ConfigDto>>
    suspend fun createConfig(dto: CreateConfigDto): APIResponse<ConfigDto?>
}

class DefaultConfigHandler(
    private val storage: ConfigStore,
    private val coroutineContext: CoroutineContext
) : ConfigHandler {
    override suspend fun getConfigsByUsername(username: String): APIResponse<List<ConfigDto>> {
        return withContext(coroutineContext) {
            val result = storage.getConfigsByUsername(username)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Configurations of user $username were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createConfig(dto: CreateConfigDto): APIResponse<ConfigDto?> {
        return withContext(coroutineContext) {
            val result = storage.createConfig(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to create configuration, try again later"
                )

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
