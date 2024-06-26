package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.common.Uuid

class ConceptSpaceUiState(
    val ontology: OntologyUiState?
)

fun emptySpace() = ConceptSpaceUiState(null)

data class InfoNodeUiState(
    val conceptId: Uuid,
    val title: String,
    val x: Float,
    val y: Float
)

data class InfoEdgeUiState(
    val relationId: Uuid,
    val label: String,
    val sourceId: Uuid,
    val targetId: Uuid,
)

class OntologyUiState(
    val id: Uuid,
    val nodes: List<InfoNodeUiState>,
    val edges: List<InfoEdgeUiState>
) {

}
