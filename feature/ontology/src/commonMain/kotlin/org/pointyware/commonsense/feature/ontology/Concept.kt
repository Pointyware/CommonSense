@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
