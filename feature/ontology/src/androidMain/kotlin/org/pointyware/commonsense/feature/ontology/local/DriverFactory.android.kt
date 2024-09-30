package org.pointyware.commonsense.feature.ontology.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.pointyware.commonsense.feature.ontology.db.OntologyDb

/**
 *
 */
actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(OntologyDb.Schema, context, "ontology.db")
    }

    actual fun inMemoryDriver(): SqlDriver {
        return AndroidSqliteDriver(OntologyDb.Schema, context)
    }
}
