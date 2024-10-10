/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface RecordsDataSource {
    suspend fun createRecord(name: String): Result<Type.Record>
    suspend fun <T:Type> addField(original: Type.Record, name: String, type: T, defaultValue: Value<T>): Result<Type.Record>
    suspend fun getRecord(id: Uuid): Result<Type.Record>

    suspend fun createInstance(template: Type.Record): Result<Value.Instance>
    suspend fun <T:Type> setAttribute(original: Value.Instance, fieldName: String, value: Value<T>): Result<Value.Instance>
}
