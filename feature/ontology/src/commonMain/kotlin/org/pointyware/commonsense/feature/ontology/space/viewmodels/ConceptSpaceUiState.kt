@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.space.viewmodels

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ConceptSpaceUiState(
    val ontology: OntologyUiState?
)

fun emptySpace() = ConceptSpaceUiState(null)

data class InfoNodeUiState(
    val instanceId: Uuid,
    val description: String,
    val modificationEnabled: Boolean,
    val x: Float,
    val y: Float
)

data class InfoEdgeUiState(
    val relationId: Uuid,
    val label: Uuid,
    val sourceId: Uuid,
    val targetId: Uuid,
)

class OntologyUiState(
    val id: Uuid,
    val nodes: List<InfoNodeUiState>,
    val edges: List<InfoEdgeUiState>
) {

}
