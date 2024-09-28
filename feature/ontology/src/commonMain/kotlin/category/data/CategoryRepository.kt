package org.pointyware.commonsense.feature.ontology.category.data

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.entities.Category

/**
 *
 */
interface CategoryRepository {
    suspend fun createCategory(name: String): Result<Category>
    suspend fun getCategory(id: Uuid): Result<Category>
    suspend fun getSubcategories(id: Uuid): Result<List<Category>>
    suspend fun getConcepts(id: Uuid): Result<List<Concept>>
    suspend fun addConcept(subject: Uuid, newConcept: Concept): Result<Unit>
    suspend fun addCategory(subject: Uuid, newCategory: Category): Result<Unit>
}

/**
 * Simple repository implementation that maintains a map of categories in memory with associated subcategories and concepts.
 */
class CategoryRepositoryImpl(

): CategoryRepository {

    data class Record(
        val category: Category,
        val subcategories: MutableList<Category>,
        val concepts: MutableList<Concept>,
    )

    private val categoryIndex = mutableMapOf<Uuid, Record>()

    override suspend fun createCategory(name: String): Result<Category> {
        val newCategory = Category(id = Uuid.v4(), name = name)
        categoryIndex[newCategory.id] = Record(
            category = newCategory,
            subcategories = mutableListOf(),
            concepts = mutableListOf(),
        )
        return Result.success(newCategory)
    }

    override suspend fun getCategory(id: Uuid): Result<Category> {
        return categoryIndex[id]?.let {
            Result.success(it.category)
        } ?: run {
            Result.failure(Exception("Category not found"))
        }
    }

    override suspend fun getSubcategories(id: Uuid): Result<List<Category>> {
        return categoryIndex[id]?.let {
            Result.success(it.subcategories.toList())
        } ?: run {
            Result.failure(Exception("Category not found"))
        }
    }

    override suspend fun getConcepts(id: Uuid): Result<List<Concept>> {
        return categoryIndex[id]?.let {
            Result.success(it.concepts.toList())
        } ?: run {
            Result.failure(Exception("Category not found"))
        }
    }

    override suspend fun addConcept(subject: Uuid, newConcept: Concept): Result<Unit> {
        categoryIndex[subject]?.let {
            it.concepts.add(newConcept)
            return Result.success(Unit)
        } ?: run {
            return Result.failure(Exception("Category not found"))
        }
    }

    override suspend fun addCategory(subject: Uuid, newCategory: Category): Result<Unit> {
        categoryIndex[subject]?.let {
            it.subcategories.add(newCategory)
            return Result.success(Unit)
        } ?: run {
            return Result.failure(Exception("Category not found"))
        }
    }
}
