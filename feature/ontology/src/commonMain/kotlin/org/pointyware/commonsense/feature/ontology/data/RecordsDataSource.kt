/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.entities.Field
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface RecordsDataSource {
    suspend fun createRecord(name: String): Result<Type.Record>
    suspend fun <T:Type> defineField(original: Type.Record, name: String, type: T, defaultValue: Value<T>?): Result<Field<T>>
    suspend fun getRecord(id: Uuid): Result<Type.Record>

    suspend fun createInstance(template: Type.Record): Result<Value.Instance>
    suspend fun <T:Type> setFieldValue(original: Value.Instance, field: Field<T>, value: Value<*>): Result<Value.Instance>
    suspend fun getInstance(id: Uuid): Result<Value.Instance>
    suspend fun getInstances(categoryId: Uuid): Result<List<Value.Instance>>
}
