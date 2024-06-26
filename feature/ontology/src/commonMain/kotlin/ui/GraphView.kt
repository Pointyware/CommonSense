package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.unit.Constraints
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.ui.components.InfoEdge
import org.pointyware.commonsense.core.ui.components.InfoEdgeState
import org.pointyware.commonsense.core.ui.components.InfoNode
import org.pointyware.commonsense.core.ui.components.InfoNodeState

/**
 */
@Composable
fun GraphView(
    infoNodes: List<InfoNodeState>,
    infoEdges: List<InfoEdgeState>,
    modifier: Modifier = Modifier,
    onModifyNode: (Uuid) -> Unit,
    onDeleteNode: (Uuid) -> Unit,
    onCompleteNode: (Uuid, String) -> Unit,
) {
    Layout(
        modifier = modifier,
        content = {
            infoNodes.forEach { node ->
                InfoNode(
                    state = node,
                    modifier = Modifier.offset(node.x, node.y),
                    onModify = onModifyNode,
                    onDelete = onDeleteNode,
                    onComplete = {
                        onCompleteNode(node.id, it)
                    },
                )
            }
        }
    ) { measurables, constraints ->
        val nodeMeasures = measurables.map {
            it.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {

        }
    }
    Layout(
        modifier = modifier,
        content = {
            infoEdges.forEach { edge ->
                InfoEdge(
                    state = edge,
                    onModify = {},
                    onDelete = {},
                    onComplete = {},
                )
            }
        }
    ) { measurables, constraints ->
        val edgeMeasures = measurables.map {
            it.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {

        }
    }
}
