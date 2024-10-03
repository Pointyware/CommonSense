package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.Category

interface CategoryDataSource {
    suspend fun createCategory(name: String): Result<Category>
    suspend fun addCategory(subject: Uuid, name: String): Result<Category>
    suspend fun getCategory(id: Uuid): Result<Category>
    suspend fun getSubcategories(id: Uuid): Result<List<Category>>
    suspend fun addConcept(subject: Uuid, name: String, description: String): Result<Concept>
    suspend fun getConcepts(id: Uuid): Result<List<Concept>>
    suspend fun removeCategories(ids: Set<Uuid>): Result<Unit>
    suspend fun removeConcepts(ids: Set<Uuid>): Result<Unit>
}
