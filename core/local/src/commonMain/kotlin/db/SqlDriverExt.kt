package org.pointyware.commonsense.core.local.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver

/**
 * Extension property to get and set the version of the database.
 */
var SqlDriver.version: Long
    get() {
        return this.executeQuery(
            identifier = null,
            sql = "PRAGMA user_version;",
            mapper = { query -> QueryResult.Value(query.getLong(0) ?: 0L) },
            parameters = 0,
            binders = null,
        ).value
    }
    set(value) {
        this.executeQuery(
            identifier = null,
            sql = "PRAGMA user_version = ?;",
            mapper = { QueryResult.Unit },
            parameters = 1,
            binders = {
                bindLong(0, value)
            },
        )
    }
