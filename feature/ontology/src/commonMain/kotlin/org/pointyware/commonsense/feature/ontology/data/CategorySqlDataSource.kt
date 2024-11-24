/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.feature.ontology.Category
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CategorySqlDataSource(
    lazyDb: Lazy<OntologyDb>
): CategoryDataSource {

    private val db: OntologyDb by lazyDb

    override suspend fun createCategory(name: String): Result<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun addCategory(subject: Uuid, name: String): Result<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategory(id: Uuid): Result<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubcategories(id: Uuid): Result<List<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeCategories(ids: Set<Uuid>): Result<Unit> {
        TODO("Not yet implemented")
    }
}
