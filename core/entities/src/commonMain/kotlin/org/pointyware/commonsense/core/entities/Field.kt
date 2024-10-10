package org.pointyware.commonsense.core.entities

/**
 * A Field is a part of a Record that defines a property of a Concept.
 */
data class Field<T:Type>(
    val name: String,
    val type: T,
    val defaultValue: Value<T>? = null
) {

}
