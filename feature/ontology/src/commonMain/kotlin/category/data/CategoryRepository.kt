package org.pointyware.commonsense.feature.ontology.category.data

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.Category

/**
 * Repository interface for managing categories and their associated subcategories and concepts.
 */
interface CategoryRepository {
    suspend fun createCategory(name: String): Result<Category>
    suspend fun getCategory(id: Uuid): Result<Category>
    suspend fun getSubcategories(id: Uuid): Result<List<Category>>
    suspend fun getConcepts(id: Uuid): Result<List<Concept>>
    suspend fun addConcept(subject: Uuid, newConcept: Concept): Result<Concept>
    suspend fun addCategory(subject: Uuid, newCategory: Category): Result<Category>
}

/**
 * Simple repository implementation that maintains a map of categories in memory with associated subcategories and concepts.
 */
class CategoryRepositoryImpl(
    private val categoryDataSource: CategoryDataSource
): CategoryRepository {

    override suspend fun createCategory(name: String): Result<Category> {
        return categoryDataSource.createCategory(name)
    }

    override suspend fun getCategory(id: Uuid): Result<Category> {
        return categoryDataSource.getCategory(id)
    }

    override suspend fun getSubcategories(id: Uuid): Result<List<Category>> {
        return categoryDataSource.getSubcategories(id)
    }

    override suspend fun getConcepts(id: Uuid): Result<List<Concept>> {
        return categoryDataSource.getConcepts(id)
    }

    override suspend fun addConcept(subject: Uuid, newConcept: Concept): Result<Concept> {
        return categoryDataSource.addConcept(subject, newConcept.name, newConcept.description ?: "")
    }

    override suspend fun addCategory(subject: Uuid, newCategory: Category): Result<Category> {
        return categoryDataSource.addCategory(subject, newCategory.name)
    }
}
