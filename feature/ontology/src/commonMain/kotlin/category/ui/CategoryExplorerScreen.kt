package org.pointyware.commonsense.feature.ontology.category.ui

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
import org.pointyware.commonsense.feature.ontology.category.viewmodels.CategoryExplorerEditorState
import org.pointyware.commonsense.feature.ontology.category.viewmodels.CategoryExplorerViewModel
import org.pointyware.commonsense.feature.ontology.ui.CategoryEditor
import org.pointyware.commonsense.feature.ontology.ui.ConceptEditor

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
    val mappedState = CategoryExplorerState(
        state.currentCategory.selected,
        state.currentCategory.subcategories,
        state.currentCategory.concepts
    )

    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val capture = state.editorState) {
            is CategoryExplorerEditorState.Concept -> {
                Dialog(
                    onDismissRequest = {
                        viewModel.onCancel()
                    }
                ) {
                    ConceptEditor(
                        state = capture.concept,
                        onNameChange = viewModel::onNameChange,
                        onDescriptionChange = viewModel::onDescriptionChange,
                        onConfirm = viewModel::onCommitConcept,
                        onCancel = viewModel::onCancel
                    )
                }
            }
            is CategoryExplorerEditorState.Category -> {
                CategoryEditor(
                    state = capture.category,
                    onNameChange = viewModel::onNameChange,
                    onConfirm = TODO(),
                    onCancel = viewModel::onCancel
                )
            }
            else -> { /* Show Nothing */ }
        }

        CategoryExplorer(
            state = mappedState,
            modifier = Modifier.fillMaxSize(),
            onCategorySelected = viewModel::onCategorySelected,
            onConceptSelected = viewModel::onConceptSelected,
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
    }
}
