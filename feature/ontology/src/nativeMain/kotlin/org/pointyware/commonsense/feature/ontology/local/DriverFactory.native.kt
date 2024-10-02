package org.pointyware.commonsense.feature.ontology.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.pointyware.commonsense.feature.ontology.db.OntologyDb

/**
 *
 */
class NativeDriverFactory : DriverFactory {
    override fun createSqlDriver(path: String): SqlDriver {
        return NativeSqliteDriver(OntologyDb.Schema, path)
    }
}
