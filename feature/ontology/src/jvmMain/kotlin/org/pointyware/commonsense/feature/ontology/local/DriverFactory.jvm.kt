package org.pointyware.commonsense.feature.ontology.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

/**
 *
 */
class JvmDriverFactory : DriverFactory {
    override fun createSqlDriver(path: String): SqlDriver {
        return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY + DATABASE_NAME)
    }
}
