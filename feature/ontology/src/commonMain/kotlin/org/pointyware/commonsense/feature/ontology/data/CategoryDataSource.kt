package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.Concept
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import org.pointyware.commonsense.core.common.Uuid as MyUuid

@OptIn(ExperimentalUuidApi::class)
interface CategoryDataSource {
    suspend fun createCategory(name: String): Result<Category>
    @Deprecated("Use addCategory(Uuid, String)")
    suspend fun addCategory(subject: MyUuid, name: String): Result<Category>
    suspend fun addCategory(subject: Uuid, name: String): Result<Category>
    @Deprecated("Use getCategory(Uuid)")
    suspend fun getCategory(id: MyUuid): Result<Category>
    suspend fun getCategory(id: Uuid): Result<Category>
    @Deprecated("Use getSubcategories(Uuid)")
    suspend fun getSubcategories(id: MyUuid): Result<List<Category>>
    suspend fun getSubcategories(id: Uuid): Result<List<Category>>
    @Deprecated("Use addConcept(Uuid, String, String)")
    suspend fun addConcept(subject: MyUuid, name: String, description: String): Result<Concept>
    suspend fun addConcept(subject: Uuid, name: String, description: String): Result<Concept>
    @Deprecated("Use getConcepts(Uuid)")
    suspend fun getConcepts(id: MyUuid): Result<List<Concept>>
    suspend fun getConcepts(id: Uuid): Result<List<Concept>>
    @Deprecated("Use removeCategories(Set<Uuid>)")
    suspend fun removeCategories(ids: Set<MyUuid>): Result<Unit>
    suspend fun removeCategories(ids: Set<Uuid>): Result<Unit>
    @Deprecated("Use removeConcepts(Set<Uuid>)")
    suspend fun removeConcepts(ids: Set<MyUuid>): Result<Unit>
    suspend fun removeConcepts(ids: Set<Uuid>): Result<Unit>
}
