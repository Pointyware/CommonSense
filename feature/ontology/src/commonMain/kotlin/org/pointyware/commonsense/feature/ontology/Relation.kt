@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology

import kotlinx.serialization.Serializable
import org.pointyware.commonsense.core.entities.Value
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * A relation between two concepts in an ontology. A relation is an edge in the ontology graph.
 * `Relation => Edge<ConceptInfo, ConceptInfo, RelationInfo>`
 * `RelationInfo => <Id, Type, Weight>`
 *
 * Sometimes we want to label an edge with an instance, but many times we want to model relations
 * as reversible statements made about the two objects. For example, "is a parent of" is a relation
 * that can be reversed to "is a child of". In this case, the source and target are the same, but the
 * type of the relation is different. Compare this with wanting to describe the distance between
 * two instances that represent points of interest in a graph. Not only is this relation undirected,
 * Since instances have fields that describe their properties, these implicitly model relationships
 * (in the case that a field type is a reference to another instance). In this case, the relation is
 * usually directed.
 */
interface Relation {
    val id: Uuid
    val source2: Value.Instance
    val target2: Value.Instance
    val label: Value.Instance
    @Deprecated("Prefer Types and Instances")
    val source: Concept
    @Deprecated("Prefer Types and Instances")
    val target: Concept
    @Deprecated("Redundant with Edge Label")
    val type: String
    @Deprecated("Redundant with Edge Label")
    val weight: RelationWeight
}

@Serializable
sealed interface RelationWeight {
    data class Fixed(val value: Double) : RelationWeight
    data object Variable : RelationWeight
}
