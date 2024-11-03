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
class RecordsRepositoryImpl(
    private val categoryDataSource: CategoryDataSource,
    private val recordsDataSource: RecordsDataSource
): RecordsRepository {
    override suspend fun addCategory(subject: Uuid, newCategory: Category): Result<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategory(categoryId: Uuid): Result<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubcategories(subjectId: Uuid): Result<List<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun createRecord(name: String): Result<Type.Record> {
        TODO("Not yet implemented")
    }

    override suspend fun addInstance(
        subject: Uuid,
        newInstance: Value.Instance
    ): Result<Value.Instance> {
        TODO("Not yet implemented")
    }

    override suspend fun getInstances(categoryId: Uuid): Result<List<Value.Instance>> {
        TODO("Not yet implemented")
    }

    override suspend fun getInstance(instanceId: Uuid): Result<Value.Instance> {
        TODO("Not yet implemented")
    }
}
