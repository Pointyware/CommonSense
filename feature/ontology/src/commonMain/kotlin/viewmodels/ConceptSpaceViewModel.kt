package org.pointyware.commonsense.ontology.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.ontology.interactors.LoadConceptSpaceUseCase

/**
 *
 */
class ConceptSpaceViewModel(
    private val loadConceptSpaceUseCase: LoadConceptSpaceUseCase,
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
                                    InfoNodeUiState(
                                        concept.id,
                                        concept.name
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

    }

    fun onModifyNode(id: String) {

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
    val title: String
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
