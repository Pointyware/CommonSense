package org.pointyware.commonsense.feature.ontology.local

import kotlinx.serialization.Serializable
import RelationWeight

/**
 * A JSON representation of the [org.pointyware.commonsense.feature.ontology.ConceptSpace] entity.
 */
@Serializable
data class ConceptSpaceJson(
    val id: String,
    val focus: OntologyJson
)


/**
 * A JSON representation of the [org.pointyware.commonsense.feature.ontology.Ontology] entity.
 */
@Serializable
data class OntologyJson(
    val id: String,
    val concepts: Set<ConceptJson>,
    val relations: Set<RelationJson>
)

/**
 * A JSON representation of the [org.pointyware.commonsense.feature.ontology.Concept] entity.
 */
@Serializable
data class ConceptJson(
    val id: String,
    val name: String,
    val description: String?,
)

/**
 * A JSON representation of the [org.pointyware.commonsense.feature.ontology.Relation] entity.
 */
@Serializable
data class RelationJson(
    val id: String,
    val name: String,
    val weight: RelationWeight,
    val source: String,
    val target: String,
)
