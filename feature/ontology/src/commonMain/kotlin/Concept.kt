package org.pointyware.commonsense.ontology

/**
 * A singular concept in an ontology. A concept is a node in the ontology graph.
 */
interface Concept {
    val id: String
    val name: String
    val description: String?
    val relations: Set<Relation>
}

class IndependentConcept(
    override val id: String,
    override val name: String,
    override val description: String?,
    override val relations: Set<Relation>,
): Concept

class MemberConcept(
    override val id: String,
    val owner: Ontology,
): Concept {
    private val self by lazy {
        owner.concepts.find { it.id == id } ?: throw IllegalStateException("Concept $id not found in owner Ontology ${owner.id}")
    }
    override val name: String
        get() = self.name
    override val description: String?
        get() = self.description
    override val relations: Set<Relation>
        get() = self.relations
}
