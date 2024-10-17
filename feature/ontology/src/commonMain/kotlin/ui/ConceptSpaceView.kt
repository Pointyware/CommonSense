@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.common.Mapper
import org.pointyware.commonsense.core.ui.components.InfoEdgeState
import org.pointyware.commonsense.core.ui.components.InfoNodeState
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptSpaceUiState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
data class ConceptSpaceViewState(
    val errorMessage: String? = null,
    val infoNodes: List<InfoNodeState>,
    val infoEdges: List<InfoEdgeState>
)

/**
 * Uses a [GraphView] and adds interaction to modify the represented graph.
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
    GraphView(
        infoNodes = state.infoNodes,
        infoEdges = state.infoEdges,
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onCreateNode(
                        offset.x / density.density,
                        offset.y / density.density
                    )
                }
            },
        onModifyNode = onModifyNode,
        onDeleteNode = onDeleteNode,
        onCompleteNode = onCompleteNode,
    )
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
