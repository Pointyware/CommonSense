package org.pointyware.commonsense.ontology.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.ontology.data.ArrangementController
import org.pointyware.commonsense.ontology.data.Position
import org.pointyware.commonsense.ontology.interactors.AddNewNodeUseCase
import org.pointyware.commonsense.ontology.interactors.GetActiveConceptSpaceUseCase
import org.pointyware.commonsense.ontology.interactors.LoadConceptSpaceUseCase
import org.pointyware.commonsense.ontology.interactors.RemoveNodeUseCase

/**
 *
 */
class ConceptSpaceViewModel(
    private val getActiveConceptSpaceUseCase: GetActiveConceptSpaceUseCase,
    private val loadConceptSpaceUseCase: LoadConceptSpaceUseCase,
    private val addNewNodeUseCase: AddNewNodeUseCase,
    private val removeNodeUseCase: RemoveNodeUseCase,
    private val arrangementController: ArrangementController
): ViewModel() {

    init {
        Log.v("ConceptSpaceViewModel created")
    }

    private val testSpace = ConceptSpaceUiState(
        OntologyUiState(
            id = "test",
            nodes = listOf(
                InfoNodeUiState("1", "Node 1", 100f, 100f),
                InfoNodeUiState("2", "Node 2", 200f, 200f),
                InfoNodeUiState("3", "Node 3", 300f, 300f),
            ),
            edges = emptyList()
        )
    )

    val state: StateFlow<ConceptSpaceUiState> = getActiveConceptSpaceUseCase().map { conceptSpace ->
        Log.v("Mapping concept space: $conceptSpace")
        ConceptSpaceUiState(
            OntologyUiState(
                id = conceptSpace.id,
                nodes = conceptSpace.focus.concepts.map { concept ->
                    val position = arrangementController.getConceptPositionOrPut(concept, 0f, 0f)
                    InfoNodeUiState(
                        concept.id,
                        concept.name,
                        position.x,
                        position.y
                    )
                },
                edges = conceptSpace.focus.relations.map { relation ->
                    InfoEdgeUiState(
                        relation.id,
                        relation.type,
                        relation.source.id,
                        relation.target.id
                    )
                }
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = testSpace
    )

    fun onLoadConceptSpace(id: String) {
        viewModelScope.launch {
            loadConceptSpaceUseCase(id)
                .onSuccess { conceptSpace ->
                    Log.v("Loaded concept space: $conceptSpace")
                }
                .onFailure { error ->
                    Log.v("Failed to load concept space: $error")
                }
        }
    }

    fun onSaveConceptSpace() {

    }

    fun onDeleteNode(id: String) {
        viewModelScope.launch {
            removeNodeUseCase(id)
        }
    }

    fun onModifyNode(id: String) {
        viewModelScope.launch {
            // TODO: update state of ui to reflect modification
            arrangementController.freeze(id)
        }
    }

    fun onCreateNode(x: Float, y: Float) {
        viewModelScope.launch {
            addNewNodeUseCase(x, y)
            mutablePointSet.update {
                it + Position(x, y)
            }
        }
    }

    private val mutablePointSet = MutableStateFlow<Set<Position>>(emptySet())
    val pointSet: StateFlow<Set<Position>> get() = mutablePointSet
}
