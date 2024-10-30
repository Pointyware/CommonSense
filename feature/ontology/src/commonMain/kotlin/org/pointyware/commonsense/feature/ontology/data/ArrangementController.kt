package org.pointyware.commonsense.feature.ontology.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.commonsense.core.entities.Value
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


data class Position(
    val x: Float,
    val y: Float
) {
}

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
interface ArrangementController {
    fun addNode(newConcept: Value.Instance, x: Float, y: Float)
    fun removeNode(id: Uuid)
    fun getConceptPosition(id: Uuid): Position?

    fun getConceptPositionOrPut(concept: Value.Instance, x: Float, y: Float): Position {
        return getConceptPosition(concept.id) ?: Position(x, y).also { addNode(concept, x, y)}
    }

    val frozenIds: StateFlow<Set<Uuid>>

    fun freeze(id: Uuid)
    fun unfreeze(id: Uuid)
}

@OptIn(ExperimentalUuidApi::class)
class SimpleArrangementController(

): ArrangementController {


    private val concepts = mutableMapOf<Value.Instance, Position>()
    override fun addNode(newConcept: Value.Instance, x: Float, y: Float) {
        concepts[newConcept] = Position(x, y)
    }

    override fun removeNode(id: Uuid) {
        concepts.remove(concepts.entries.find { it.key.id == id }?.key)
    }

    override fun getConceptPosition(id: Uuid): Position? {
        return concepts.entries.find { it.key.id == id }?.value
    }

    private val mutableFrozenIds = MutableStateFlow(emptySet<Uuid>())
    override val frozenIds: StateFlow<Set<Uuid>>
        get() = mutableFrozenIds

    override fun freeze(id: Uuid) {
        mutableFrozenIds.value += id
    }

    override fun unfreeze(id: Uuid) {
        mutableFrozenIds.value -= id
    }
}
