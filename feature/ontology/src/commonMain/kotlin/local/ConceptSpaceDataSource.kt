package org.pointyware.commonsense.ontology.local

import kotlinx.coroutines.flow.Flow
import org.pointyware.commonsense.core.local.KmpFile
import org.pointyware.commonsense.ontology.Concept
import org.pointyware.commonsense.ontology.ConceptSpace

/**
 *
 */
interface ConceptSpaceDataSource {
    val activeSpace: Flow<ConceptSpace>
    suspend fun loadConceptSpace(file: KmpFile): Result<ConceptSpace>
    suspend fun saveConceptSpace(file: KmpFile): Result<Unit>
    suspend fun createNode(name: String): Result<Concept>
    suspend fun removeNode(id: String): Result<Unit>
}

class ConceptSpaceNotFoundException(id: String) : Exception("Concept space with id $id not found")

fun generateRandomId(): String {
    return (0..5).map { ('a'..'z').random() }.joinToString("") // TODO: replace with UUIDs?
}
