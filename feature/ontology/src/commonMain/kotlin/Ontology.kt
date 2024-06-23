package org.pointyware.commonsense.ontology

import org.pointyware.commonsense.ontology.local.generateRandomId

/**
 * A collection of concepts and their relations, which usually form a hierarchy of concepts.
 */
interface Ontology {
    val id: String
    val concepts: Set<Concept>
    val relations: Set<Relation>
}

interface MutableOntology: Ontology {
    fun addConcept(concept: Concept)
    fun addRelation(conceptSource: String, conceptTarget: String)
}

internal class SimpleMutableOntology(
    override val id: String,
): MutableOntology {

    private val conceptMap: MutableMap<String, Concept> = mutableMapOf()
    override val concepts: Set<Concept> get() = conceptMap.values.toSet()

    private val relationMap: MutableMap<String, Relation> = mutableMapOf()
    override val relations: Set<Relation> get() = relationMap.values.toSet()

    override fun addConcept(concept: Concept) {
        conceptMap[concept.id]?.let { tenant ->
            throw IllegalArgumentException("Concept ${tenant.id} already exists in Ontology $id")
        } ?: run {
            conceptMap[concept.id] = concept
        }
    }

    override fun addRelation(conceptSource: String, conceptTarget: String) {
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
}

/**
 * Create a new mutable ontology with the given [id] and apply the [init] block to it.
 */
fun mutableOntology(id: String, init: MutableOntology.() -> Unit = {}): MutableOntology {
    val ontology = SimpleMutableOntology(id)
    ontology.init()
    return ontology
}
