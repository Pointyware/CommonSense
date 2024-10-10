/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value

interface RecordsDataSource {
    suspend fun createRecord(name: String): Result<Type.Record>
    suspend fun <T:Type> addField(recordId: Uuid, name: String, type: T, defaultValue: Value<T>): Result<Type.Record>
    suspend fun getRecord(id: Uuid): Result<Type.Record>

    suspend fun createInstance(recordId: Uuid, name: String): Result<Value.Instance>
    suspend fun <T:Type> setAttribute(instanceId: Uuid, fieldId: Uuid, value: Value<T>): Result<Value.Instance>
}
