package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.common.Mapper
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.ui.components.InfoNode
import org.pointyware.commonsense.core.ui.components.InfoNodeState
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptSpaceUiState

/**
 *
 */
data class ConceptSpaceViewState(
    val errorMessage: String? = null,
    val infoNodes: List<InfoNodeState>,
    val infoEdges: List<InfoEdgeState>
)

/**
 *
 */
@Composable
fun ConceptSpaceView(
    state: ConceptSpaceViewState,
    modifier: Modifier = Modifier,
    onDeleteNode: (Uuid) -> Unit,
    onModifyNode: (Uuid) -> Unit,
    onCompleteNode: (Uuid,String) -> Unit,
    onCreateNode: (Float,Float) -> Unit,
) {
    Log.v("ConceptSpaceView")
    val density = LocalDensity.current
    /*
    TODO: Create GraphView
      1. Create a GraphView composable that takes Node and Edge states
      2. Create an internal Node composable that takes a Node state and geometry cache for writing
      3. Create an internal Edge composable that takes an Edge state and geometry cache for reading
     */
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onCreateNode(
                        offset.x / density.density,
                        offset.y / density.density
                    )
                }
            }
    ) {
        state.infoNodes.forEach { nodeState ->
            InfoNode(
                state = nodeState,
                modifier = Modifier.offset(nodeState.x, nodeState.y),
                onModify = onModifyNode,
                onDelete = onDeleteNode,
                onComplete = {
                    onCompleteNode(nodeState.id, it)
                },
            )
        }
    }
}

object ConceptSpaceUiStateMapper: Mapper<ConceptSpaceUiState, ConceptSpaceViewState> {

    override fun map(input: ConceptSpaceUiState): ConceptSpaceViewState {
        Log.v("Mapping concept space ui state: $input")
        val infoNodes = input.ontology?.nodes?.map { node ->
            InfoNodeState(
                id = node.conceptId,
                title = node.title,
                x = node.x.dp,
                y = node.y.dp,
            )
        } ?: emptyList()

        return ConceptSpaceViewState(
            infoNodes = infoNodes,
            infoEdges = emptyList()
        )
    }
}
