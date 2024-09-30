package org.pointyware.commonsense.feature.ontology.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.pointyware.commonsense.feature.ontology.db.OntologyDb

/**
 *
 */
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(OntologyDb.Schema, DATABASE_NAME)
    }
}
