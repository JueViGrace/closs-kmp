package org.closs.core.database.helper

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.closs.core.database.ClossSvDb
import org.closs.core.database.Closs_user
import org.closs.core.database.driver.DriverFactory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DbHelper(
    private val driver: DriverFactory,
    private val scope: CoroutineScope
) {
    private var db: ClossSvDb? = null
    private val mutex = Mutex()
    private val password = System.getenv("SYSTEM_PASSWORD")

    @OptIn(ExperimentalUuidApi::class)
    private val userSeed = Closs_user(
        id = Uuid.random().toHexString(),
        username = "System",
        password = Bcrypt.hash(input = password, saltRounds = 12).decodeToString(),
        codigo = "G100",
        role = "",
        ult_sinc = "",
        version = "",
        created_at = "",
        updated_at = "",
        deleted_at = ""
    )

    suspend fun <Result> withDatabase(block: suspend DbHelper.(ClossSvDb) -> Result): Result = mutex.withLock {
        if (db == null) {
            db = createDb()
        }

        return@withLock block(db!!)
    }

    private suspend fun createDb(): ClossSvDb {
        val driver = ClossSvDb(driver.createDriver())
        scope.async {
            driver.transaction {
                driver.clossUserQueries.insert(userSeed).executeAsOneOrNull()
            }
        }.await()
        return driver
    }

    suspend fun <T : Any> executeOne(query: Query<T>): T? {
        return scope.async {
            query.executeAsOneOrNull()
        }.await()
    }

    suspend fun <T : Any> executeOneAsFlow(query: Query<T>): Flow<T?> {
        return scope.async {
            query.asFlow().mapToOneOrNull(coroutineContext)
        }.await()
    }

    suspend fun <T : Any> executeList(query: Query<T>): List<T> {
        return scope.async {
            query.executeAsList()
        }.await()
    }

    suspend fun <T : Any> executeListAsFlow(query: Query<T>): Flow<List<T>> {
        return scope.async {
            query.asFlow().mapToList(coroutineContext)
        }.await()
    }
}
