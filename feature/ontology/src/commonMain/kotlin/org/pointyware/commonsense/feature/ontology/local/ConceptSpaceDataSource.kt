@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.local

import kotlinx.coroutines.flow.Flow
import kotlinx.io.files.Path
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.ConceptSpace
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
interface ConceptSpaceDataSource {
    val activeSpace: Flow<ConceptSpace>
    suspend fun loadConceptSpace(file: Path): Result<ConceptSpace>
    suspend fun saveConceptSpace(file: Path): Result<Unit>
    suspend fun createNode(name: String): Result<Concept>
    suspend fun removeNode(id: Uuid): Result<Unit>
    suspend fun updateNode(id: Uuid, name: String, description: String?): Result<Unit>
}

class ConceptSpaceNotFoundException(id: String) : Exception("Concept space with id $id not found")

fun generateRandomId(): Uuid {
    return Uuid.random()
}
