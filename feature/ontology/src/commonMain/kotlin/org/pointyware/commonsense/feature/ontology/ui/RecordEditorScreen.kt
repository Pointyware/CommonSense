package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.feature.ontology.viewmodels.RecordEditorViewModel

/**
 * Allows a user to create a new type of record.
 */
@Composable
fun RecordEditorScreen(
    viewModel: RecordEditorViewModel
) {
    val state by viewModel.state.collectAsState()
    RecordEditor(
        state = state,
        modifier = Modifier.fillMaxSize(),
        onNameChange = viewModel::onRecordNameChange,
        onFieldAdded = viewModel::onFieldAdded,
        onFieldNameChange = viewModel::onFieldNameChange,
        onFieldTypeChanged = viewModel::onFieldTypeChange,
        onFieldValueChanged = viewModel::onFieldValueChange,
        onFieldRemoved = viewModel::onFieldRemoved,
        onConfirm = viewModel::onConfirm,
        onCancel = viewModel::onCancel,
    )
}
