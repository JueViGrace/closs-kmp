package org.closs.salesman.data.handler

import kotlinx.coroutines.withContext
import org.closs.core.shared.types.salesman.CreateSalesmanDto
import org.closs.core.shared.types.salesman.CreateSalesmanStatisticDto
import org.closs.core.shared.types.salesman.SalesmanDto
import org.closs.core.shared.types.salesman.SalesmanStatisticsDto
import org.closs.core.shared.types.salesman.UpdateSalesmanDto
import org.closs.core.shared.types.salesman.UpdateSalesmanStatisticDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.salesman.data.store.SalesmanStore
import kotlin.coroutines.CoroutineContext

interface SalesmanHandler {
    suspend fun getSalesmanByManager(manager: String): APIResponse<List<SalesmanDto>>
    suspend fun getSalesmanByCode(code: String): APIResponse<SalesmanDto?>
    suspend fun getExistingSalesmanByCode(code: String): APIResponse<SalesmanDto?>
    suspend fun createSalesman(dto: CreateSalesmanDto): APIResponse<SalesmanDto?>
    suspend fun updateSalesman(dto: UpdateSalesmanDto): APIResponse<SalesmanDto?>
    suspend fun getStatisticsByManager(manager: String): APIResponse<List<SalesmanStatisticsDto>>
    suspend fun getStatisticsBySalesman(code: String): APIResponse<SalesmanStatisticsDto?>
    suspend fun createSalesmanStatistics(dto: CreateSalesmanStatisticDto): APIResponse<SalesmanStatisticsDto?>
    suspend fun updateSalesmanStatistics(dto: UpdateSalesmanStatisticDto): APIResponse<SalesmanStatisticsDto?>
}

class DefaultSalesmanHandler(
    private val storage: SalesmanStore,
    private val coroutineContext: CoroutineContext
) : SalesmanHandler {
    override suspend fun getSalesmanByManager(manager: String): APIResponse<List<SalesmanDto>> {
        return withContext(coroutineContext) {
            val result = storage.getSalesmanByManager(manager)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Salesmen were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getSalesmanByCode(code: String): APIResponse<SalesmanDto?> {
        return withContext(coroutineContext) {
            val result = storage.getSalesmanByCode(code)
                ?: return@withContext ServerResponse.notFound(
                    message = "Salesman with code $code was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getExistingSalesmanByCode(code: String): APIResponse<SalesmanDto?> {
        return withContext(coroutineContext) {
            val result = storage.getExistingSalesmanByCode(code)
                ?: return@withContext ServerResponse.notFound(
                    message = "Salesman with code $code was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createSalesman(dto: CreateSalesmanDto): APIResponse<SalesmanDto?> {
        return withContext(coroutineContext) {
            val result = storage.createSalesman(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to create salesman, try again"
                )

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun updateSalesman(dto: UpdateSalesmanDto): APIResponse<SalesmanDto?> {
        return withContext(coroutineContext) {
            val result = storage.updateSalesman(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to update salesman, try again"
                )

            ServerResponse.accepted(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getStatisticsByManager(manager: String): APIResponse<List<SalesmanStatisticsDto>> {
        return withContext(coroutineContext) {
            val result = storage.getStatisticsByManager(manager)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Salesmen statistics were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getStatisticsBySalesman(code: String): APIResponse<SalesmanStatisticsDto?> {
        return withContext(coroutineContext) {
            val result = storage.getStatisticsBySalesman(code)
                ?: return@withContext ServerResponse.notFound(
                    message = "Salesman statistics of code $code was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createSalesmanStatistics(
        dto: CreateSalesmanStatisticDto
    ): APIResponse<SalesmanStatisticsDto?> {
        return withContext(coroutineContext) {
            val result = storage.createSalesmanStatistics(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to create salesman statistics, try again"
                )

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun updateSalesmanStatistics(
        dto: UpdateSalesmanStatisticDto
    ): APIResponse<SalesmanStatisticsDto?> {
        return withContext(coroutineContext) {
            val result = storage.updateSalesmanStatistics(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to update salesman statistics, try again"
                )

            ServerResponse.accepted(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
