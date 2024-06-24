package org.pointyware.commonsense.ontology.local

import org.pointyware.commonsense.ontology.Concept
import org.pointyware.commonsense.ontology.ConceptSpace

/**
 *
 */
interface ConceptSpaceDataSource {
    suspend fun loadConceptSpace(id: String): Result<ConceptSpace>
    suspend fun saveConceptSpace(space: ConceptSpace): Result<Unit>
    suspend fun createNode(name: String): Result<Concept>
    suspend fun removeNode(id: String): Result<Unit>
}

class ConceptSpaceNotFoundException(id: String) : Exception("Concept space with id $id not found")

fun generateRandomId(): String {
    return (0..5).map { ('a'..'z').random() }.joinToString("") // TODO: replace with UUIDs?
}
