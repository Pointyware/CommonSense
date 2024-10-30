/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.Category
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
interface RecordsRepository {
    suspend fun createRecord(name: String): Result<Type.Record>
    suspend fun addCategory(subject: Uuid, newCategory: Category): Result<Category>
    suspend fun getCategory(categoryId: Uuid): Result<Category>
    suspend fun getSubcategories(id: Uuid): Result<List<Category>>
    suspend fun addInstance(subject: Uuid, newInstance: Value.Instance): Result<Value.Instance>
    suspend fun getInstances(id: Uuid): Result<List<Value.Instance>>
}
