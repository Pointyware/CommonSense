/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.clickable
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
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.core.ui.IntField
import org.pointyware.commonsense.feature.ontology.viewmodels.FieldEditorUiState

/**
 * Allows the user to edit a single field as a row item in a column scope
 */
@Composable
fun FieldEditorRowItem(
    state: FieldEditorUiState<*>,
    modifier: Modifier = Modifier,
    onFieldNameChange: (String)->Unit,
    typeList: List<Type>,
    onFieldTypeChange: (Type)->Unit,
    onFieldValueChange: (Value<*>)->Unit,
    onRemove: ()->Unit
) {
    Row(
        modifier = modifier
    ) {
        TextField(
            value = state.name,
            onValueChange = onFieldNameChange
        )

        when (val capture = state.value) {
            is Value.IntValue -> {
                IntField(
                    value = capture,
                    onValueChange = onFieldValueChange
                )
            }
            else -> {
                println("Unsupported type: $capture")
            }
        }

        var selectType by remember { mutableStateOf(false) }
        Button(
            onClick = { selectType = true }
        ) {
            DropdownMenu(
                expanded = selectType,
                onDismissRequest = { selectType = false }
            ) {
                typeList.forEach { type ->
                    Row(
                        modifier = Modifier.clickable { onFieldTypeChange(type) }
                    ) {
                        Text(type.name)
                    }
                }
            }
            Text(
                text = "Select Type"
            )
        }

        Button(
            onClick = onRemove
        ) {
            Text(
                text = "Remove"
            )
        }
    }
}
