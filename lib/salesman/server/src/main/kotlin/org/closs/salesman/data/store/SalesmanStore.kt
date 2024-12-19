package org.closs.salesman.data.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.salesman.CreateSalesmanDto
import org.closs.core.shared.types.salesman.statistics.CreateSalesmanStatisticDto
import org.closs.core.shared.types.salesman.SalesmanDto
import org.closs.core.shared.types.salesman.statistics.SalesmanStatisticsDto
import org.closs.core.shared.types.salesman.UpdateSalesmanDto
import org.closs.core.shared.types.salesman.statistics.UpdateSalesmanStatisticDto
import org.closs.core.types.salesman.createDtoToDbSalesman
import org.closs.core.types.salesman.existingSalesmanByCodeToDto
import org.closs.core.types.salesman.salesmanByCodeToDto
import org.closs.core.types.salesman.salesmanByManagerToDto
import org.closs.core.types.salesman.toDb
import org.closs.core.types.salesman.toDto

interface SalesmanStore {
    suspend fun getSalesmenByManager(manager: String): List<SalesmanDto>
    suspend fun getExistingSalesmenByManager(manager: String): List<SalesmanDto>
    suspend fun getSalesmanByCode(code: String): SalesmanDto?
    suspend fun getExistingSalesmanByCode(code: String): SalesmanDto?
    suspend fun createSalesman(dto: CreateSalesmanDto): SalesmanDto?
    suspend fun updateSalesman(dto: UpdateSalesmanDto): SalesmanDto?
    suspend fun getStatisticsByManager(manager: String): List<SalesmanStatisticsDto>
    suspend fun getStatisticsBySalesman(code: String): SalesmanStatisticsDto?
    suspend fun createSalesmanStatistics(dto: CreateSalesmanStatisticDto): SalesmanStatisticsDto?
    suspend fun updateSalesmanStatistics(dto: UpdateSalesmanStatisticDto): SalesmanStatisticsDto?
}

class DefaultSalesmanStore(
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : SalesmanStore {
    override suspend fun getSalesmenByManager(manager: String): List<SalesmanDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossSalesmanQueries.findSalesmenByManager(
                    manager = manager,
                ),
            ).map { salesman -> salesman.salesmanByManagerToDto() }
        }
    }

    override suspend fun getExistingSalesmenByManager(manager: String): List<SalesmanDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossSalesmanQueries.findExistingSalesmenByManager(
                    manager = manager,
                ),
            ).map { salesman -> salesman.salesmanByManagerToDto() }
        }
    }

    override suspend fun getSalesmanByCode(code: String): SalesmanDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossSalesmanQueries.findSalesmanByCode(
                    code = code
                ),
            )?.salesmanByCodeToDto()
        }
    }

    override suspend fun getExistingSalesmanByCode(code: String): SalesmanDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossSalesmanQueries.findExistingSalesmanByCode(
                    code = code
                ),
            )?.existingSalesmanByCodeToDto()
        }
    }

    override suspend fun createSalesman(dto: CreateSalesmanDto): SalesmanDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbSalesman = db.clossSalesmanQueries.insert(
                        closs_salesman = dto.createDtoToDbSalesman()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)
                    getExistingSalesmanByCode(dbSalesman.codigo)
                }
            }
        }.await()
    }

    override suspend fun updateSalesman(dto: UpdateSalesmanDto): SalesmanDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbSalesman = db.clossSalesmanQueries.update(
                        nombre = dto.nombre,
                        telefono = dto.telefono,
                        telefonos = dto.telefonos,
                        supervpor = dto.supervpor,
                        sector = dto.sector,
                        subcodigo = dto.subcodigo,
                        email = dto.email,
                        code = dto.codigo
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)
                    getExistingSalesmanByCode(dbSalesman.codigo)
                }
            }
        }.await()
    }

    override suspend fun getStatisticsByManager(manager: String): List<SalesmanStatisticsDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossSalesmanStatisticQueries.findStatisticsByManager(
                    manager = manager
                )
            ).map { statistic -> statistic.toDto() }
        }
    }

    override suspend fun getStatisticsBySalesman(code: String): SalesmanStatisticsDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossSalesmanStatisticQueries.findStatisticsBySalesman(
                    code = code
                )
            )?.toDto()
        }
    }

    override suspend fun createSalesmanStatistics(dto: CreateSalesmanStatisticDto): SalesmanStatisticsDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbStatistic = db.clossSalesmanStatisticQueries.insert(
                        closs_salesman_statistic = dto.toDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)
                    getStatisticsBySalesman(dbStatistic.vendedor)
                }
            }
        }.await()
    }

    override suspend fun updateSalesmanStatistics(dto: UpdateSalesmanStatisticDto): SalesmanStatisticsDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbStatistic = db.clossSalesmanStatisticQueries.update(
                        codcoord = dto.codcoord,
                        nomcoord = dto.nomcoord,
                        nombrevend = dto.nombrevend,
                        cntpedidos = dto.cntpedidos.toLong(),
                        mtopedidos = dto.mtopedidos,
                        cntfacturas = dto.cntfacturas.toLong(),
                        mtofacturas = dto.mtofacturas,
                        metavend = dto.metavend,
                        prcmeta = dto.prcmeta,
                        cntclientes = dto.cntclientes.toLong(),
                        clivisit = dto.clivisit.toLong(),
                        prcvisitas = dto.prcvisitas,
                        lom_montovtas = dto.lomMontovtas,
                        lom_prcvtas = dto.lomPrcvtas,
                        lom_prcvisit = dto.lomPrcvisit,
                        rlom_montovtas = dto.rlomMontovtas,
                        rlom_prcvtas = dto.rlomPrcvtas,
                        rlom_prcvisit = dto.rlomPrcvisit,
                        fecha_estad = dto.fechaEstad,
                        ppgdol_totneto = dto.ppgdolTotneto,
                        devdol_totneto = dto.devdolTotneto,
                        defdol_totneto = dto.defdolTotneto,
                        totdolcob = dto.totdolcob,
                        cntrecl = dto.cntrecl.toLong(),
                        mtorecl = dto.mtorecl,
                        code = dto.vendedor,
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)
                    getStatisticsBySalesman(dbStatistic.vendedor)
                }
            }
        }.await()
    }
}
