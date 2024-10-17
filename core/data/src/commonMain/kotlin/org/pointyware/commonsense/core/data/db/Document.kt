@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.core.data.org.pointyware.commonsense.core.data.db

import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents a document that can be stored in a graph database. A node in a graph.
 */
interface Document {
    val id: Uuid
    val type: DocumentType
    val attributes: Map<String, Value<*>>
}

class PropertyKey(val name: String, val type: Type)

/**
 *
 */
interface DocumentType {
    val id: Uuid
    val properties: Map<String, Type>
    val parent: DocumentType?
}
