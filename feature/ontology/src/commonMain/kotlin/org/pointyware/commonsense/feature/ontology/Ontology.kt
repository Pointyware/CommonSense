package org.pointyware.commonsense.feature.ontology

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.local.generateRandomId

/**
 * A collection of concepts and their relations, which usually form a rough hierarchy of concepts,
 * but more generally a graph of concepts.
 *
 * `Ontology => Graph<ConceptInfo, RelationInfo>`
 */
interface Ontology {
    val id: Uuid
    val concepts: Set<Concept>
    val relations: Set<Relation>
}

interface MutableOntology: Ontology {
    fun addConcept(concept: Concept)
    fun removeConcept(id: Uuid)
    fun addRelation(conceptSource: Uuid, conceptTarget: Uuid)
    fun updateConcept(concept: Concept)
}

internal class SimpleMutableOntology(
    override val id: Uuid,
): MutableOntology {

    private val conceptMap: MutableMap<Uuid, Concept> = mutableMapOf()
    override val concepts: Set<Concept> get() = conceptMap.values.toSet()

    private val relationMap: MutableMap<Uuid, Relation> = mutableMapOf()
    override val relations: Set<Relation> get() = relationMap.values.toSet()

    override fun addConcept(concept: Concept) {
        conceptMap[concept.id]?.let { tenant ->
            throw IllegalArgumentException("Concept ${tenant.id} already exists in Ontology $id")
        } ?: run {
            conceptMap[concept.id] = concept
        }
    }

    override fun removeConcept(id: Uuid) {
        conceptMap.remove(id)?.let {
            relationMap.remove(it.id)
        }
    }

    override fun addRelation(conceptSource: Uuid, conceptTarget: Uuid) {
        conceptMap[conceptSource]?.let { source ->
            conceptMap[conceptTarget]?.let { target ->
                val newRelation = MemberRelation(
                    id = generateRandomId(),
                    owner = this
                )
                relationMap[newRelation.id] = newRelation
            } ?: throw IllegalArgumentException("Concept target $conceptTarget not found in Ontology $id")
        } ?: throw IllegalArgumentException("Concept source $conceptSource not found in Ontology $id")
    }

    override fun updateConcept(concept: Concept) {
         conceptMap[id]?.also {
            conceptMap[id] = concept
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
