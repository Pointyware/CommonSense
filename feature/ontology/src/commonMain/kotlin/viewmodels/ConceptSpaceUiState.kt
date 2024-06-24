package org.pointyware.commonsense.ontology.viewmodels

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
