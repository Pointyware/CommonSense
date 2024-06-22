package org.pointyware.commonsense.ontology.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.core.common.Mapper
import org.pointyware.commonsense.core.ui.components.InfoNode
import org.pointyware.commonsense.core.ui.components.InfoNodeState
import org.pointyware.commonsense.ontology.viewmodels.ConceptSpaceUiState

/**
 *
 */
data class ConceptSpaceViewState(
    val errorMessage: String? = null,
    val infoNodes: List<InfoNodeState>,
    val infoEdges: List<InfoEdgeState>
)

data class InfoEdgeState(
    val id: String,
    val from: InfoNodeState,
    val to: InfoNodeState
)

/**
 *
 */
@Composable
fun ConceptSpaceView(
    state: ConceptSpaceViewState,
    modifier: Modifier = Modifier,
    onDeleteNode: (String) -> Unit,
    onModifyNode: (String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        state.infoNodes.forEach {
            InfoNode(
                state = it,
                onModify = onModifyNode,
                onDelete = onDeleteNode
            )
        }
        state.infoEdges.forEach {

        }
    }
}

object ConceptSpaceUiStateMapper: Mapper<ConceptSpaceUiState, ConceptSpaceViewState> {

    override fun map(input: ConceptSpaceUiState): ConceptSpaceViewState {
        when (input) {
            is ConceptSpaceUiState.Empty -> {
                return ConceptSpaceViewState(
                    infoNodes = emptyList(),
                    infoEdges = emptyList()
                )
            }
            is ConceptSpaceUiState.Loaded -> {
                val infoNodes = input.ontology?.nodes?.map { node ->
                    InfoNodeState(
                        id = node.conceptId,
                        title = node.title
                    )
                } ?: emptyList()

                // Convert InfoNodeUiState to InfoNodeViewState
                val nodeViewList = input.ontology?.nodes?.map { node ->
                    InfoNodeState(
                        id = node.conceptId,
                        title = node.title
                    )
                } ?: emptyList()
                val nodeMap = nodeViewList.associateBy { it.id }

                val infoEdges = input.ontology?.edges?.map { edge ->
                    val from = nodeMap[edge.sourceId] ?: error("Node not found: ${edge.sourceId}")
                    val to = nodeMap[edge.targetId] ?: error("Node not found: ${edge.targetId}")
                    InfoEdgeState(
                        id = edge.relationId,
                        from = from,
                        to = to
                    )
                } ?: emptyList()


                return ConceptSpaceViewState(
                    infoNodes = infoNodes,
                    infoEdges = infoEdges
                )
            }
            is ConceptSpaceUiState.Error -> {
                return ConceptSpaceViewState(
                    errorMessage = input.error.toString(),
                    infoNodes = emptyList(),
                    infoEdges = emptyList()
                )
            }
        }
    }
}