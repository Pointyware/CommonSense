package org.pointyware.commonsense.feature.ontology.category.data

import org.pointyware.commonsense.feature.ontology.entities.Category

interface CategoryDataSource {
    suspend fun createCategory(name: String): Result<Category>
//    suspend fun getCategory(id: Uuid): Result<Category>
//    suspend fun getSubcategories(id: Uuid): Result<List<Category>>
//    suspend fun getConcepts(id: Uuid): Result<List<Concept>>
//    suspend fun addConcept(subject: Uuid, newConcept: Concept): Result<Unit>
//    suspend fun addCategory(subject: Uuid, newCategory: Category): Result<Unit>
}
