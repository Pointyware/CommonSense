@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology

import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.local.generateRandomId
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * A collection of concepts and their relations, which usually form a rough hierarchy of concepts,
 * but more generally a graph of concepts.
 *
 * `Ontology => Graph<ConceptInfo, RelationInfo>`
 */
interface Ontology {
    val id: Uuid
    val instances: Set<Value.Instance>
    val relations: Set<Relation>
}

interface MutableOntology: Ontology {
    fun addInstance(instance: Value.Instance)
    fun removeInstance(id: Uuid)
    fun addRelation(conceptSource: Uuid, conceptTarget: Uuid)
    fun updateInstance(instance: Value.Instance)
}

internal class SimpleMutableOntology(
    override val id: Uuid,
): MutableOntology {

    private val instanceMap: MutableMap<Uuid, Value.Instance> = mutableMapOf()
    override val instances: Set<Value.Instance> get() = instanceMap.values.toSet()

    private val relationMap: MutableMap<Uuid, Relation> = mutableMapOf()
    override val relations: Set<Relation> get() = relationMap.values.toSet()

    override fun addInstance(instance: Value.Instance) {
        instanceMap[instance.id]?.let { tenant ->
            throw IllegalArgumentException("Instance ${tenant.id} already exists in Ontology $id")
        } ?: run {
            instanceMap[instance.id] = instance
        }
    }

    override fun removeInstance(id: Uuid) {
        instanceMap.remove(id) // TODO: add/remove relations
    }

    override fun addRelation(conceptSource: Uuid, conceptTarget: Uuid) {
        instanceMap[conceptSource]?.let { source ->
            instanceMap[conceptTarget]?.let { target ->
                val newRelation = MemberRelation(
                    id = generateRandomId(),
                    owner = this
                )
                relationMap[newRelation.id] = newRelation
            } ?: throw IllegalArgumentException("Concept target $conceptTarget not found in Ontology $id")
        } ?: throw IllegalArgumentException("Concept source $conceptSource not found in Ontology $id")
    }

    override fun updateInstance(instance: Value.Instance) {
        instanceMap[id]?.also {
            instanceMap[id] = instance
        } ?: throw IllegalArgumentException("Concept $id not found in Ontology $id")
    }
}

/**
 * Create a new mutable ontology with the given [id] and apply the [init] block to it.
 */
fun mutableOntology(id: Uuid, init: MutableOntology.() -> Unit = {}): MutableOntology {
    val ontology = SimpleMutableOntology(id)
    ontology.init()
    return ontology
}
