package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorUiState

/**
 * Allows a user to edit the properties of a new or existing concept.
 */
@Composable
fun ConceptEditor(
    state: ConceptEditorUiState,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
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
        TextField(
            label = { Text("Description") },
            value = state.description,
            onValueChange = onDescriptionChange,
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
