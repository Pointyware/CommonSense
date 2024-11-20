package org.pointyware.commonsense.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind


data class InfoEdgeState(
    val id: String,
    val label: String?,
    val from: InfoNodeState,
    val to: InfoNodeState
)

/**
 *
 */
@Composable
fun InfoEdge(
    state: InfoEdgeState,
    modifier: Modifier = Modifier,
    onModify: () -> Unit,
    onDelete: () -> Unit,
    onComplete: () -> Unit,
) {
    Box(
        modifier = modifier
            .drawBehind {
                // TODO: draw edge
            }
    ) {
        state.label?.let { label ->
            Text(text = label)
        }
        // TODO: add edge controls
    }
}
