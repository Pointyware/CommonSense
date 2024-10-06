package org.pointyware.commonsense.core.entities

/**
 * A form of Schema that is used to define the structure of a Concept.
 *
 * Each Concept belongs to a Class and has a set of Properties.
 */
data class Record(
    override val name: String,
    val fields: Set<Field<*>> = emptySet()
): Type {
}
