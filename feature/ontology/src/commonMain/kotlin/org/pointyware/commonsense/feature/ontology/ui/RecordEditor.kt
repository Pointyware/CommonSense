/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Type.Record
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.viewmodels.RecordEditorUiState

/**
 * Allows a user to create a new [Record] or edit an existing [Record].
 */
@Composable
fun RecordEditor(
    state: RecordEditorUiState,
    modifier: Modifier = Modifier,
    onNameChange: (String)->Unit,
    onFieldAdded: ()->Unit,
    onFieldNameChange: (index: Int, newName: String)->Unit,
    onFieldTypeChanged: (index: Int, newType: Type)->Unit,
    onFieldValueChanged: (index: Int, newValue: Value<*>)->Unit,
    onFieldRemoved: (index: Int)->Unit,
    onConfirm: ()->Unit,
    onCancel: ()->Unit,
) {
    Column(
        modifier = modifier
            .semantics { contentDescription = "Record Editor" }
    ) {
        TextField(
            value = state.name,
            onValueChange = onNameChange
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(state.fields) { index, item ->
                FieldEditorRowItem(
                    state = item,
                    onFieldNameChange = { onFieldNameChange(index, it) },
                    typeList = state.availableTypes,
                    onFieldTypeChange = { onFieldTypeChanged(index, it) },
                    onFieldValueChange = { onFieldValueChanged(index, it) },
                    onRemove = { onFieldRemoved(index) }
                )
            }
            item {
                Button(
                    onClick = onFieldAdded
                ) {
                    Text(
                        text = "Add Field"
                    )
                }
            }
        }
        Row(

        ) {
            Button(
                onClick = onCancel
            ) {
                Text(
                    text = "Cancel"
                )
            }
            Button(
                onClick = onConfirm
            ) {
                Text(
                    text = "Confirm"
                )
            }
        }
    }
}
