package org.closs.company.data.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.company.CompanyDto
import org.closs.core.shared.types.company.CreateCompanyDto
import org.closs.core.shared.types.company.UpdateCompanyDto
import org.closs.core.types.company.dbCompanyToDto
import org.closs.core.types.company.dtoToDbCompany

interface CompanyStore {
    suspend fun getCompanyByCode(code: String): CompanyDto?
    suspend fun createCompany(dto: CreateCompanyDto): CompanyDto?
    suspend fun updateCompany(dto: UpdateCompanyDto): CompanyDto?
}

class DefaultCompanyStore(
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper,
) : CompanyStore {
    override suspend fun getCompanyByCode(code: String): CompanyDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossCompanyQueries.findExistingCompanyByCode(
                    code = code
                )
            )?.dbCompanyToDto()
        }
    }

    override suspend fun createCompany(dto: CreateCompanyDto): CompanyDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbCompany = db.clossCompanyQueries.insert(
                        closs_company = dto.dtoToDbCompany()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)

                    getCompanyByCode(dbCompany.ked_codigo)
                }
            }
        }.await()
    }

    override suspend fun updateCompany(dto: UpdateCompanyDto): CompanyDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbCompany = db.clossCompanyQueries.update(
                        code = dto.code,
                        name = dto.name,
                        status = dto.status.toLong(),
                        domain = dto.domain,
                        agency = dto.agency
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)

                    getCompanyByCode(dbCompany.ked_codigo)
                }
            }
        }.await()
    }
}
