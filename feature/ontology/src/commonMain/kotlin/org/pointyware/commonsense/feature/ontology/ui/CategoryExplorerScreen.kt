package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryExplorerEditorState
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryExplorerViewModel

/**
 * Takes a view model, binds the state to the CategoryExplorer composable, and passes events
 * back to the view model.
 */
@Composable
fun CategoryExplorerScreen(
    viewModel: CategoryExplorerViewModel
) {
    val state by viewModel.state.collectAsState()
    val isLoading = state.loading // TODO: add loading state indicator

    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CategoryExplorer(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onCategorySelected = viewModel::onCategorySelected,
            onConceptSelected = viewModel::onConceptSelected,
            onDeleteSelected = viewModel::onDeleteSelected,
        )

        Column(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Button(
                onClick = viewModel::onAddCard,
            ) {
                Text(
                    text = "New Concept"
                )
            }
            Button(
                onClick = viewModel::onAddCategory,
            ) {
                Text(
                    text = "New Category"
                )
            }
        }

        when (val capture = state.editorState) {
            is CategoryExplorerEditorState.Record -> {

                Dialog(
                    onDismissRequest = {
                        viewModel.onCancelEditor()
                    }
                ) {
                    RecordEditor(
                        state = capture.record,
                        onNameChange = viewModel::onRecordNameChange,
                        onFieldAdded = viewModel::addField,
                        onFieldNameChange = viewModel::setFieldName,
                        onFieldTypeChanged = viewModel::setFieldType,
                        onFieldValueChanged = viewModel::setFieldValue,
                        onFieldRemoved = viewModel::removeField,
                    )
                }
            }
            is CategoryExplorerEditorState.Category -> {
                Dialog(
                    onDismissRequest = {
                        viewModel.onCancelEditor()
                    }
                ) {
                    CategoryEditor(
                        state = capture.category,
                        onNameChange = viewModel::onCategoryNameChange,
                        onConfirm = viewModel::onCommitCategory,
                        onCancel = viewModel::onCancelEditor
                    )
                }
            }
            else -> { /* Show Nothing */ }
        }
    }
}
