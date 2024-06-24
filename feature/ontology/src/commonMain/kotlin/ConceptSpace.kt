package org.pointyware.commonsense.ontology

/**
 * A space to explore concepts and document relationships between them to create an ontology.
 */
interface ConceptSpace {
    val id: String
    val focus: Ontology
}

class MutableConceptSpace(
    override val id: String,
    override val focus: MutableOntology,
): ConceptSpace {

}
