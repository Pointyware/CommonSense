@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * A relation between two concepts in an ontology. A relation is an edge in the ontology graph.
 * `Relation => Edge<ConceptInfo, ConceptInfo, RelationInfo>`
 * `RelationInfo => <Id, Type, Weight>`
 */
interface Relation {
    val id: Uuid
    val source: Concept
    val target: Concept
    val type: String
    val weight: RelationWeight
}

@Serializable
sealed interface RelationWeight {
    data class Fixed(val value: Double) : RelationWeight
    data object Variable : RelationWeight
}

class MemberRelation(
    override val id: Uuid,
    val owner: Ontology,
): Relation {
    private val self by lazy {
        owner.relations.find { it.id == id } ?: throw IllegalStateException("Relation $id not found in owner Ontology ${owner.id}")
    }
    override val source: Concept
        get() = self.source
    override val target: Concept
        get() = self.target
    override val type: String
        get() = self.type
    override val weight: RelationWeight
        get() = self.weight
}
