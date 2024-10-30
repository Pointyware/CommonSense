@file:OptIn(ExperimentalUuidApi::class)
@file:UseSerializers(UuidSerializer::class)

package org.pointyware.commonsense.feature.ontology.local

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.pointyware.commonsense.core.local.UuidSerializer
import org.pointyware.commonsense.feature.ontology.RelationWeight
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
    val instances: Set<InstanceJson>,
    val relations: Set<RelationJson>
)

/**
 * A JSON representation of the [org.pointyware.commonsense.feature.ontology.Instance] entity.
 */
@Serializable
data class InstanceJson(
    val id: Uuid,
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
