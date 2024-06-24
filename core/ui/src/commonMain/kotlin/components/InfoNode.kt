package org.pointyware.commonsense.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.pointyware.commonsense.core.common.Log

/**
 *
 */
data class InfoNodeState( // TODO: add modification state
    val id: String,
    val title: String,
    val x: Dp,
    val y: Dp
) {
}

/**
 * Displays some content with a slot in the default appearance.
 */
@Composable
fun InfoNode(
    state: InfoNodeState,
    modifier: Modifier = Modifier,
    onModify: (String) -> Unit,
    onDelete: (String) -> Unit,
) { // TODO: add modification/deletion buttons and reflect modification state with TextInput
    Log.v("InfoNode")
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            ),
    ) {
        Text(text = state.title)
    }
}
