/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * TODO: describe purpose/intent of InstanceEditor
 */
@Composable
fun InstanceEditor(
    modifier: Modifier = Modifier,
) {
    /*

        Box {
            var expanded by remember { mutableStateOf(false) }
            Button(onClick = { expanded = true }) {
                Text("Select Template")
            } // TODO: load templates and allow user to select existing one or (New Template)
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
    Text(text = "Concept")
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
        val saveEnabled = remember(state.name, state.description) { state.name.isNotBlank() && state.description.isNotBlank() }
        Button(
            onClick = onConfirm,
            enabled = saveEnabled
        ) {
            Text("Save")
        }
    }
     */
}
