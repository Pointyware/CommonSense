package org.pointyware.commonsense.core.data.db

import org.pointyware.commonsense.core.data.org.pointyware.commonsense.core.data.db.Document
import org.pointyware.commonsense.core.data.org.pointyware.commonsense.core.data.db.DocumentType
import org.pointyware.commonsense.core.data.org.pointyware.commonsense.core.data.db.Relation
import org.pointyware.commonsense.core.entities.Type
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * This specialized database is designed to store and query documents that are connected in a graph.
 *
 * Coarse features include the ability to register types, create instances of those types, and
 * create relations between instances. A general "note" type is provided for unstructured data.
 */
@OptIn(ExperimentalUuidApi::class)
interface DocumentGraphDatabase {

    /**
     * Registers a new type of document in the database.
     *
     * For now only class-like relationships are supported. Each document type allows a single
     * optional parent. The parent type must be registered before the child type. Child properties
     * can not conflict with parent properties.
     *
     * TODO: allow specifying defaults?
     *
     * @param name The name of the type.
     * @param properties The properties of the type.
     * @param parent The parent type of the new type.
     * @return The UUID of the new type.
     */
    suspend fun registerType(name: String, properties: Map<String, Type>, parent: Uuid?): Result<DocumentType>

    /**
     * Modifies the existing type identified by the [id] with the given [name] if provided, and
     * any [properties] that are provided.
     *
     * The given [properties] should contain all the final properties of the type. Any properties
     * excluded will be dropped from the type.
     */
    suspend fun modifyType(id: Uuid, name: String? = null, properties: Map<String, Type>): Result<DocumentType>

    /**
     * Creates a new document of the given type.
     *
     * @param type The type of the document.
     * @param attributes The attributes of the document.
     * @return The UUID of the new document.
     */
    suspend fun createDocument(type: DocumentType, attributes: Map<String, Any>): Result<Document>

    suspend fun createDocuments() // TODO:

    /**
     * Creates a relation between two documents.
     *
     * @param from The UUID of the source document.
     * @param to The UUID of the target document.
     * @param type The UUID of the relation type.
     */
    suspend fun createRelation(from: Uuid, to: Uuid, type: Uuid, label: Any): Result<Relation>

    suspend fun createRelations() // TODO:

    /**
     * Create a new edge between two documents.
     */
    suspend fun createRelation(from: Uuid, to: Uuid, label: String): Result<Relation>

    /**
     * Retrieves a document by its UUID.
     *
     * @param id The UUID of the document.
     * @return The document, or DocumentDoesNotExistError if the document does not exist.
     */
    suspend fun getDocument(id: Uuid): Result<Document>

    /**
     * Retrieves all documents of a given type.
     *
     * @param type The UUID of the type.
     * @return A list of documents.
     */
    suspend fun getDocumentsOfType(type: Uuid): List<Document>

    /**
     * Retrieve all edges from a document.
     */
    suspend fun getRelationsFrom(id: Uuid): Result<List<Relation>>

    /**
     * Retrieve all edges to a document.
     */
    suspend fun getRelationsTo(id: Uuid): Result<List<Relation>>

    /**
     * Retrieves all relations that are related to or from a given document.
     *
     * @param id The UUID of the document.
     * @return A list of related relations.
     */
    suspend fun getDocumentRelations(id: Uuid): Result<List<Relation>>

    /**
     * Retrieves all relations of a given type.
     *
     * @param type The UUID of the type.
     * @return A list of relations.
     */
    suspend fun getRelationsOfType(type: Uuid): List<Relation>

    /**
     * Update a document in the database.
     */
    suspend fun updateDocument(document: Document): Result<Document>

    /**
     * Removes a relation from the database.
     *
     * @param id The UUID of the relation.
     */
    suspend fun removeRelation(id: Uuid)

    suspend fun removeRelations() // TODO:

    /**
     * Removes all documents of a given type from the database.
     *
     * @param type The UUID of the type.
     */
    suspend fun removeDocumentsOfType(type: Uuid)

    suspend fun removeDocuments() // TODO:

    /**
     * Removes all relations of a given type from the database.
     *
     * @param type The UUID of the type.
     */
    suspend fun removeRelationsOfType(type: Uuid)

    /**
     * Removes all relations that are related to a given document from the database.
     *
     * @param id The UUID of the document.
     */
    suspend fun removeRelated(id: Uuid)

    /**
     * Remove a document from the database.
     * @param id The UUID of the document.
     */
    suspend fun removeDocument(id: Uuid): Result<Unit>

    // region Advanced

    suspend fun filterDocuments(type: Uuid, filter: (Document) -> Boolean): List<Document>

    suspend fun filterRelations(type: Uuid, filter: (Relation) -> Boolean): List<Relation>

    suspend fun traverseBfs(start: Uuid, type: Uuid, maxDepth: Int): List<Document>
    suspend fun traverseDfs(start: Uuid, type: Uuid, maxDepth: Int): List<Document>

    interface StructuralQuery
    suspend fun findMatching(query: StructuralQuery): List<Document>

    // endregion
}

// TODO: add more exception types for helpful errors
