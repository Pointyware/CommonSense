package org.pointyware.commonsense.feature.ontology.viewmodels

class ConceptSpaceUiState(
    val ontology: OntologyUiState?
)

fun emptySpace() = ConceptSpaceUiState(null)

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
