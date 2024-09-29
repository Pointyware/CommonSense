package org.pointyware.commonsense.feature.ontology.local

import app.cash.sqldelight.db.SqlDriver

/**
 *
 */
expect class DriverFactory {
    fun createDriver(): SqlDriver
}
