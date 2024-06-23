package org.pointyware.commonsense.ontology.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.ontology.data.ArrangementController
import org.pointyware.commonsense.ontology.interactors.AddNewNodeUseCase
import org.pointyware.commonsense.ontology.interactors.LoadConceptSpaceUseCase
import org.pointyware.commonsense.ontology.interactors.RemoveNodeUseCase

/**
 *
 */
class ConceptSpaceViewModel(
    private val loadConceptSpaceUseCase: LoadConceptSpaceUseCase,
    private val addNewNodeUseCase: AddNewNodeUseCase,
    private val removeNodeUseCase: RemoveNodeUseCase,
    private val arrangementController: ArrangementController
): ViewModel() {

    private val mutableState = MutableStateFlow<ConceptSpaceUiState>(ConceptSpaceUiState.Empty)
    val state: StateFlow<ConceptSpaceUiState> get() = mutableState

    fun onLoadConceptSpace(id: String) {
        viewModelScope.launch {
            loadConceptSpaceUseCase(id)
                .onSuccess { conceptSpace ->
                    mutableState.value =
                        ConceptSpaceUiState.Loaded(
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
                }
                .onFailure { error ->
                    mutableState.value = ConceptSpaceUiState.Error(error)
                }
        }
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
        }
    }
}

sealed class ConceptSpaceUiState(
    val ontology: OntologyUiState?,
    val error: Throwable? = null
) {
    data object Empty : ConceptSpaceUiState(null)

    class Loaded(
        ontology: OntologyUiState,
    ): ConceptSpaceUiState(ontology)

    class Error(
        error: Throwable
    ): ConceptSpaceUiState(null, error)
}

data class InfoNodeUiState(
    val conceptId: String,
    val title: String,
    val x: Float,
    val y: Float
)

data class InfoEdgeUiState(
    val relationId: String,
    val label: String,
    val sourceId: String,
    val targetId: String,
)

class OntologyUiState(
    val id: String,
    val nodes: List<InfoNodeUiState>,
    val edges: List<InfoEdgeUiState>
) {

}
