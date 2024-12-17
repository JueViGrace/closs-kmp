package org.closs.customer.data.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.customer.CreateCustomerDto
import org.closs.core.shared.types.customer.CustomerDto
import org.closs.core.types.customer.toDb
import org.closs.core.types.customer.toDto

interface CustomerStore {
    suspend fun getCustomerByCode(code: String): CustomerDto?
    suspend fun getCustomersByManager(manager: String): List<CustomerDto>
    suspend fun getCustomersBySalesman(code: String): List<CustomerDto>
    suspend fun createCustomer(dto: CreateCustomerDto): CustomerDto?
}

class DefaultCustomerStore(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : CustomerStore {
    override suspend fun getCustomerByCode(code: String): CustomerDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossCustomerQueries.findCustomerByCode(code)
            )?.toDto()
        }
    }

    override suspend fun getCustomersByManager(manager: String): List<CustomerDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossCustomerQueries.findCustomersByManager(manager)
            ).map { customer -> customer.toDto() }
        }
    }

    override suspend fun getCustomersBySalesman(code: String): List<CustomerDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossCustomerQueries.findCustomersBySalesman(code)
            ).map { customer -> customer.toDto() }
        }
    }

    override suspend fun createCustomer(dto: CreateCustomerDto): CustomerDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbCustomer = db.clossCustomerQueries.insert(
                        closs_customer = dto.toDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)
                    getCustomerByCode(dbCustomer.codigo)
                }
            }
        }.await()
    }
}
