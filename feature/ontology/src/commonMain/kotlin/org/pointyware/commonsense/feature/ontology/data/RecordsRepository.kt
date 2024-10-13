/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.entities.Type

/**
 *
 */
interface RecordsRepository {
    suspend fun createRecord(name: String): Result<Type.Record>

}
