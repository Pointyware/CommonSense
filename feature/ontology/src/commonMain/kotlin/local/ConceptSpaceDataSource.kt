package org.pointyware.commonsense.feature.ontology.local

import kotlinx.coroutines.flow.Flow
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.local.LocalStorage
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.ConceptSpace

/**
 *
 */
interface ConceptSpaceDataSource {
    val activeSpace: Flow<ConceptSpace>
    suspend fun loadConceptSpace(file: LocalStorage): Result<ConceptSpace>
    suspend fun saveConceptSpace(file: LocalStorage): Result<Unit>
    suspend fun createNode(name: String): Result<Concept>
    suspend fun removeNode(id: Uuid): Result<Unit>
    suspend fun updateNode(id: Uuid, name: String, description: String?): Result<Unit>
}

class ConceptSpaceNotFoundException(id: String) : Exception("Concept space with id $id not found")

fun generateRandomId(): Uuid {
    return Uuid.v4()
}
