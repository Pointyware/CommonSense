package org.pointyware.commonsense.ontology

/**
 *
 */
interface Relation {
    val id: String
    val source: Concept
    val target: Concept
    val type: String
    val weight: RelationWeight
}

sealed interface RelationWeight {
    data class Fixed(val value: Double) : RelationWeight
    data object Variable : RelationWeight
}
