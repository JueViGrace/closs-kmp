package org.closs.company.data.handler

import kotlinx.coroutines.withContext
import org.closs.company.data.storage.CompanyStore
import org.closs.core.shared.types.company.CompanyDto
import org.closs.core.shared.types.company.CreateCompanyDto
import org.closs.core.shared.types.company.UpdateCompanyDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import kotlin.coroutines.CoroutineContext

interface CompanyHandler {
    suspend fun getCompanyByCode(code: String): APIResponse<CompanyDto?>
    suspend fun createCompany(dto: CreateCompanyDto): APIResponse<CompanyDto?>
    suspend fun updateCompany(dto: UpdateCompanyDto): APIResponse<CompanyDto?>
}

class DefaultCompanyHandler(
    private val storage: CompanyStore,
    private val coroutineContext: CoroutineContext
) : CompanyHandler {
    override suspend fun getCompanyByCode(code: String): APIResponse<CompanyDto?> {
        return withContext(coroutineContext) {
            val result = storage.getCompanyByCode(code)
                ?: return@withContext ServerResponse.notFound(
                    message = "Company with id $code not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createCompany(dto: CreateCompanyDto): APIResponse<CompanyDto?> {
        return withContext(coroutineContext) {
            val result = storage.createCompany(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to create company, try again"
                )

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun updateCompany(dto: UpdateCompanyDto): APIResponse<CompanyDto?> {
        return withContext(coroutineContext) {
            val result = storage.updateCompany(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to update company, try again"
                )

            ServerResponse.accepted(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
