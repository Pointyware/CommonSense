package org.pointyware.commonsense.core.data.org.pointyware.commonsense.core.data.db

import org.pointyware.commonsense.core.entities.Type
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents a relation between two entities in a graph database. An edge in a graph.
 */
@OptIn(ExperimentalUuidApi::class)
interface Relation {
    val from: Uuid
    val to: Uuid
    val type: Type
    val value: Any
}
