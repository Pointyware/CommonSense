package org.pointyware.commonsense.ontology.data

import org.pointyware.commonsense.ontology.Concept


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
    fun getConceptPosition(id: String): Position?

    fun getConceptPositionOrPut(concept: Concept, x: Float, y: Float): Position {
        return getConceptPosition(concept.id) ?: Position(x, y).also { addNode(concept, x, y)}
    }
}


class SimpleArrangementController(

): ArrangementController {


    private val concepts = mutableMapOf<Concept, Position>()
    override fun addNode(newConcept: Concept, x: Float, y: Float) {
        concepts[newConcept] = Position(x, y)
    }

    override fun getConceptPosition(id: String): Position? {
        return concepts.entries.find { it.key.id == id }?.value
    }
}
