package org.pointyware.commonsense.feature.ontology.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.pointyware.commonsense.feature.ontology.db.OntologyDb

/**
 *
 */
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY + DATABASE_NAME)
        OntologyDb.Schema.create(driver)
        return driver
    }
}
