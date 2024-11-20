@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.pointyware.commonsense.core.common.Log
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
data class InfoNodeState(
    val id: Uuid,
    val title: String,
    val x: Dp,
    val y: Dp,
    val modificationEnabled: Boolean = false,
) {
}

/**
 * Displays some content with a slot in the default appearance.
 */
@Composable
fun InfoNode(
    state: InfoNodeState,
    modifier: Modifier = Modifier,
    onModify: (Uuid) -> Unit,
    onDelete: (Uuid) -> Unit,
    onComplete: (String) -> Unit,
) {
    Log.v("InfoNode")
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            ),
    ) {
        if (state.modificationEnabled) {
            var modifiedValue by remember { mutableStateOf(state.title) }
            Column() {
                TextField(
                    value = state.title,
                    onValueChange = { it -> modifiedValue = it}
                )
                Button(onClick = { onComplete(modifiedValue) }) {
                    Text(text = "Done")
                }
            }
        } else {
            Column() {
                Text(text = state.title)
                Button(onClick = { onModify(state.id) }) {
                    Text(text = "Modify")
                }
                Button(onClick = { onDelete(state.id) }) {
                    Text(text = "Delete")
                }
            }
        }
    }
}
