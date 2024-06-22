package org.pointyware.commonsense.ontology

/**
 * A collection of concepts and their relations, which usually form a hierarchy of concepts.
 */
interface Ontology {
    val id: String
    val concepts: Set<Concept>
    val relations: Set<Relation>
}

class MutableOntology(
    override val id: String,
    override val concepts: MutableSet<Concept> = mutableSetOf(),
    override val relations: MutableSet<Relation> = mutableSetOf()
): Ontology {

}
