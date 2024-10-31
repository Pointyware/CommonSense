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
 * Manages the records and instances of a user's ontology and the categories they are arranged in.
 *
 * An ontology is a tree structure where each node is a category and the leaves are records or
 * instances.
 */
@OptIn(ExperimentalUuidApi::class)
interface RecordsRepository { // TODO: rename to OntologyRepository?
    /**
     * Create a new category within the given [subject] category.
     */
    suspend fun addCategory(subject: Uuid, newCategory: Category): Result<Category>

    /**
     * Returns the category indicated by the given [categoryId].
     */
    suspend fun getCategory(categoryId: Uuid): Result<Category>

    /**
     * Returns the subcategories of the category indicated by the given [subjectId].
     */
    suspend fun getSubcategories(subjectId: Uuid): Result<List<Category>>

    /**
     * Creates a new [Type.Record] with the given [name] in the root category.
     */
    suspend fun createRecord(name: String): Result<Type.Record>

    /**
     * Creates a new [Value.Instance] in the category indicated by the given [subject].
     */
    suspend fun addInstance(subject: Uuid, newInstance: Value.Instance): Result<Value.Instance>

    /**
     * Returns a list of instances in the category indicated by the given [categoryId].
     */
    suspend fun getInstances(categoryId: Uuid): Result<List<Value.Instance>>

    /**
     * Returns the instance indicated by the given [instanceId].
     */
    suspend fun getInstance(instanceId: Uuid): Result<Value.Instance>
}
