package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryEditorUiState

/**
 * Allows a user to edit the properties of a new or existing concept.
 */
@Composable
fun CategoryEditor(
    state: CategoryEditorUiState,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        TextField(
            label = { Text("Name") },
            value = state.name,
            onValueChange = onNameChange,
        )

        Row {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
            Button(onClick = onConfirm) {
                Text("Confirm")
            }
        }
    }
}
