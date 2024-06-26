package org.pointyware.commonsense.core.ui.components

import androidx.compose.runtime.Composable


data class InfoEdgeState(
    val id: String,
    val from: InfoNodeState,
    val to: InfoNodeState
)

/**
 *
 */
@Composable
fun InfoEdge(
    state: InfoEdgeState,
    onModify: () -> Unit,
    onDelete: () -> Unit,
    onComplete: () -> Unit,
) {
    // TODO: Draw elements of edge without worrying about placement; edge layout will take care of it
}
