package org.pointyware.commonsense.ontology.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.pointyware.commonsense.core.common.Log
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
    Log.v("ConceptSpaceView")
    val density = LocalDensity.current
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
        state.infoNodes.forEach {
            InfoNode(
                state = it,
                modifier = Modifier.offset(it.x, it.y),
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
