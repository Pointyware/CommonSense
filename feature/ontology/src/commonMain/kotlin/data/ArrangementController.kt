package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Concept


data class Position(
    val x: Float,
    val y: Float
) {
}

/**
 *
 */
interface ArrangementController {
    fun addNode(newConcept: Concept, x: Float, y: Float)
    fun removeNode(id: Uuid)
    fun getConceptPosition(id: Uuid): Position?

    fun getConceptPositionOrPut(concept: Concept, x: Float, y: Float): Position {
        return getConceptPosition(concept.id) ?: Position(x, y).also { addNode(concept, x, y)}
    }

    fun freeze(id: Uuid)
}


class SimpleArrangementController(

): ArrangementController {


    private val concepts = mutableMapOf<Concept, Position>()
    override fun addNode(newConcept: Concept, x: Float, y: Float) {
        concepts[newConcept] = Position(x, y)
    }

    override fun removeNode(id: Uuid) {
        concepts.remove(concepts.entries.find { it.key.id == id }?.key)
    }

    override fun getConceptPosition(id: Uuid): Position? {
        return concepts.entries.find { it.key.id == id }?.value
    }

    override fun freeze(id: Uuid) {
        TODO("Not yet implemented")
    }
}
