package org.closs.core.database.driver

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.closs.core.database.ClossSvDb

class DriverFactory {
    suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:closs.db")
        ClossSvDb.Schema.awaitCreate(driver)
        return driver
    }
}
