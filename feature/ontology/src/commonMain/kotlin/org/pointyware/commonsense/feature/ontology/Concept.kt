package org.pointyware.commonsense.feature.ontology

import org.pointyware.commonsense.core.common.Uuid

/**
 * A singular concept in an ontology. A concept is a node in the ontology graph.
 * `Concept => Node<ConceptInfo>`
 * `ConceptInfo => <Id, Name, Description>`
 */
interface Concept {
    val id: Uuid
    val name: String
    val description: String?
}

data class IndependentConcept(
    override val id: Uuid,
    override val name: String,
    override val description: String?,
): Concept

class MemberConcept(
    override val id: Uuid,
    val owner: Ontology,
): Concept {
    private val self by lazy {
        owner.concepts.find { it.id == id } ?: throw IllegalStateException("Concept $id not found in owner Ontology ${owner.id}")
    }
    override val name: String
        get() = self.name
    override val description: String?
        get() = self.description
}
