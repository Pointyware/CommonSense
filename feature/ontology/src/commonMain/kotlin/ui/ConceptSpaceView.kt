package org.pointyware.commonsense.ontology.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
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
    onModifyNode: (String) -> Unit,
    onCreateNode: (Float,Float) -> Unit,
) {
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onCreateNode(offset.x, offset.y)
                }
            }
    ) {
        state.infoNodes.forEach {
            InfoNode(
                state = it,
                onModify = onModifyNode,
                onDelete = onDeleteNode
            )
        }
        state.infoEdges.forEach {
            // TODO: draw each edge

        }
    }
}

object ConceptSpaceUiStateMapper: Mapper<ConceptSpaceUiState, ConceptSpaceViewState> {

    override fun map(input: ConceptSpaceUiState): ConceptSpaceViewState {
        val infoNodes = input.ontology?.nodes?.map { node ->
            InfoNodeState(
                id = node.conceptId,
                title = node.title
            )
        } ?: emptyList()
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
}
