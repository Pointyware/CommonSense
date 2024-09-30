package org.pointyware.commonsense.feature.ontology.local

import app.cash.sqldelight.db.SqlDriver


internal const val DATABASE_NAME = "ontology.db"

/**
 *
 */
expect class DriverFactory {
    fun createDriver(): SqlDriver
}
