package org.pointyware.commonsense.feature.ontology

import org.pointyware.commonsense.core.common.Uuid

/**
 * A space to explore concepts and document relationships between them to create an ontology.
 */
interface ConceptSpace {
    val id: Uuid
    val focus: Ontology
}

class MutableConceptSpace(
    override val id: Uuid,
    override val focus: MutableOntology,
): ConceptSpace {

}
