package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.Concept
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Repository interface for managing categories and their associated subcategories, types, and instances.
 */
@OptIn(ExperimentalUuidApi::class)
interface CategoryRepository {
    suspend fun createCategory(name: String): Result<Category>
    suspend fun getCategory(id: Uuid): Result<Category>
    suspend fun getSubcategories(id: Uuid): Result<List<Category>>
    @Deprecated("Prefer Types and Instances")
    suspend fun getConcepts(id: Uuid): Result<List<Concept>>
    @Deprecated("Prefer Types and Instances")
    suspend fun addConcept(subject: Uuid, newConcept: Concept): Result<Concept>
    suspend fun addCategory(subject: Uuid, newCategory: Category): Result<Category>
    suspend fun removeCategories(ids: Set<Uuid>): Result<Unit>
    @Deprecated("Prefer Types and Instances")
    suspend fun removeConcepts(ids: Set<Uuid>): Result<Unit>

    /**
     * Create a new type with the given name in the given category [subject].
     */
    suspend fun registerType(subject: Uuid, name: String): Result<Type>

    /**
     * List types in the given category [subject].
     */
    suspend fun getTypes(subject: Uuid): Result<List<Type>>

    /**
     * Remove types with the given [ids].
     */
    suspend fun removeTypes(ids: Set<Uuid>): Result<Unit>

    /**
     * Attempts to add a new record
     */
    suspend fun addInstance(subject: Uuid, instance: Value.Instance): Result<Value.Instance>

    /**
     * List instances in the given category [subject].
     */
    suspend fun getInstances(subject: Uuid): Result<List<Value.Instance>>

    /**
     * Remove instances with the given [ids].
     */
    suspend fun removeInstances(ids: Set<Uuid>): Result<Unit>
}

/**
 * Simple repository implementation that maintains a map of categories in memory with associated subcategories and concepts.
 */
@OptIn(ExperimentalUuidApi::class)
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

    override suspend fun removeCategories(ids: Set<Uuid>): Result<Unit> {
        return categoryDataSource.removeCategories(ids)
    }

    override suspend fun removeConcepts(ids: Set<Uuid>): Result<Unit> {
        return categoryDataSource.removeConcepts(ids)
    }

    override suspend fun registerType(subject: Uuid, name: String): Result<Type> {
        TODO("Not yet implemented")
    }

    override suspend fun getTypes(subject: Uuid): Result<List<Type>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeTypes(ids: Set<Uuid>): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun addInstance(
        subject: Uuid,
        instance: Value.Instance
    ): Result<Value.Instance> {
        TODO("Not yet implemented")
    }

    override suspend fun getInstances(subject: Uuid): Result<List<Value.Instance>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeInstances(ids: Set<Uuid>): Result<Unit> {
        TODO("Not yet implemented")
    }
}
