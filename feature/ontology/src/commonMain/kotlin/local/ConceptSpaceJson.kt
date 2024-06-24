package org.pointyware.commonsense.ontology.local

import kotlinx.serialization.Serializable
import org.pointyware.commonsense.ontology.RelationWeight

/**
 * A JSON representation of the [org.pointyware.commonsense.ontology.ConceptSpace] entity.
 */
@Serializable
data class ConceptSpaceJson(
    val id: String,
    val focus: OntologyJson
)


/**
 * A JSON representation of the [org.pointyware.commonsense.ontology.Ontology] entity.
 */
@Serializable
data class OntologyJson(
    val id: String,
    val concepts: Set<ConceptJson>,
    val relations: Set<RelationJson>
)

/**
 * A JSON representation of the [org.pointyware.commonsense.ontology.Concept] entity.
 */
@Serializable
data class ConceptJson(
    val id: String,
    val name: String,
    val description: String?,
)

/**
 * A JSON representation of the [org.pointyware.commonsense.ontology.Relation] entity.
 */
@Serializable
data class RelationJson(
    val id: String,
    val name: String,
    val weight: RelationWeight,
    val source: String,
    val target: String,
)
