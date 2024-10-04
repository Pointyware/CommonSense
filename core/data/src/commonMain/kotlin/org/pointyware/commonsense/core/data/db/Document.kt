package org.pointyware.commonsense.core.data.org.pointyware.commonsense.core.data.db

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.entities.Type

/**
 * Represents a document that can be stored in a graph database. A node in a graph.
 */
interface Document {
    val id: Uuid
    val type: Uuid
    val attributes: Map<String, Any>
}

class PropertyKey(val name: String, val type: Type)

/**
 *
 */
interface DocumentType {
    val id: Uuid
    val properties: Map<String, Type>
}
