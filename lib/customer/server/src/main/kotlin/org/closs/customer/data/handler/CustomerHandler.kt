package org.closs.customer.data.handler

import kotlinx.coroutines.withContext
import org.closs.core.shared.types.customer.CreateCustomerDto
import org.closs.core.shared.types.customer.CustomerDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.customer.data.store.CustomerStore
import kotlin.coroutines.CoroutineContext

interface CustomerHandler {
    suspend fun getCustomerByCode(code: String): APIResponse<CustomerDto?>
    suspend fun getCustomersByManager(manager: String): APIResponse<List<CustomerDto>>
    suspend fun getCustomersBySalesman(code: String): APIResponse<List<CustomerDto>>
    suspend fun createCustomer(dto: CreateCustomerDto): APIResponse<CustomerDto?>
}

class DefaultCustomerHandler(
    private val storage: CustomerStore,
    private val coroutineContext: CoroutineContext
) : CustomerHandler {
    override suspend fun getCustomerByCode(code: String): APIResponse<CustomerDto?> {
        return withContext(coroutineContext) {
            val result = storage.getCustomerByCode(code)
                ?: return@withContext ServerResponse.notFound(
                    message = "Customer with code $code was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getCustomersByManager(manager: String): APIResponse<List<CustomerDto>> {
        return withContext(coroutineContext) {
            val result = storage.getCustomersByManager(manager)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Customers of manager $manager were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getCustomersBySalesman(code: String): APIResponse<List<CustomerDto>> {
        return withContext(coroutineContext) {
            val result = storage.getCustomersBySalesman(code)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Customers of salesman $code were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createCustomer(dto: CreateCustomerDto): APIResponse<CustomerDto?> {
        return withContext(coroutineContext) {
            val result = storage.createCustomer(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to create customer, try again"
                )

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
