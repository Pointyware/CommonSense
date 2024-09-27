package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        Box {
            var expanded by remember { mutableStateOf(false) }
            Button(onClick = { expanded = true }) {
                Text("Select Template")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Button(
                    onClick = { expanded = false }
                ) {
                    Text("Concept")
                }
            }
        }
        TextField(
            label = { Text("Name") },
            value = state.name,
            onValueChange = onNameChange,
            singleLine = true,
        )
        TextField(
            label = { Text("Description") },
            value = state.description,
            onValueChange = onDescriptionChange,
            singleLine = true,
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
