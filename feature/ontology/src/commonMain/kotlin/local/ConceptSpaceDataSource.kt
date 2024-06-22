package org.pointyware.commonsense.ontology.local

import org.pointyware.commonsense.ontology.Concept
import org.pointyware.commonsense.ontology.ConceptSpace

/**
 *
 */
interface ConceptSpaceDataSource {
    suspend fun loadConceptSpace(id: String): Result<ConceptSpace>
    suspend fun saveConceptSpace(space: ConceptSpace): Result<Unit>
    fun createNode(name: String): Result<Concept>
}

class ConceptSpaceNotFoundException(id: String) : Exception("Concept space with id $id not found")
