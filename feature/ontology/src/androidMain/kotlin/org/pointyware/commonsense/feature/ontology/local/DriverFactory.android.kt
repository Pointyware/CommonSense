package org.pointyware.commonsense.feature.ontology.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.pointyware.commonsense.feature.ontology.db.OntologyDb

/**
 *
 */
class AndroidDriverFactory(private val context: Context): DriverFactory {
    override fun createSqlDriver(path: String): SqlDriver {
        return if (path.isEmpty()) {
            AndroidSqliteDriver(OntologyDb.Schema, context)
        } else {
            AndroidSqliteDriver(OntologyDb.Schema, context, path)
        }
    }
}
