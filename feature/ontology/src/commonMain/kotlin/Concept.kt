package org.pointyware.commonsense.ontology

/**
 * A singular concept in an ontology. A concept is a node in the ontology graph.
 */
class Concept(
    val id: String,
    val name: String,
    val description: String?,
    val relations: Set<Relation>
) {

}
