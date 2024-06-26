package org.pointyware.commonsense.feature.ontology.local

import kotlinx.serialization.Serializable
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.RelationWeight

/**
 * A JSON representation of the [org.pointyware.commonsense.feature.ontology.ConceptSpace] entity.
 */
@Serializable
data class ConceptSpaceJson(
    val id: Uuid,
    val focus: OntologyJson
)


/**
 * A JSON representation of the [org.pointyware.commonsense.feature.ontology.Ontology] entity.
 */
@Serializable
data class OntologyJson(
    val id: Uuid,
    val concepts: Set<ConceptJson>,
    val relations: Set<RelationJson>
)

/**
 * A JSON representation of the [org.pointyware.commonsense.feature.ontology.Concept] entity.
 */
@Serializable
data class ConceptJson(
    val id: Uuid,
    val name: String,
    val description: String?,
)

/**
 * A JSON representation of the [org.pointyware.commonsense.feature.ontology.Relation] entity.
 */
@Serializable
data class RelationJson(
    val id: Uuid,
    val name: String,
    val weight: RelationWeight,
    val source: Uuid,
    val target: Uuid,
)
