package org.pointyware.commonsense.feature.ontology.category.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import org.pointyware.commonsense.feature.ontology.category.viewmodels.CategoryExplorerViewModel

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

    if (state.conceptEditor != null) {
        Dialog(
            onDismissRequest = {  }
        ) {

//            val conceptEditorViewModel = remember { viewModel.getConceptEditorViewModel(null) }
//            val editorState by conceptEditorViewModel.state.collectAsState()
//
//            ConceptEditor(
//                state = editorState,
//                onNameChange = conceptEditorViewModel::onNameChange,
//                onDescriptionChange = conceptEditorViewModel::onDescriptionChange,
//                onConfirm = conceptEditorViewModel::onConfirm,
//                onCancel = conceptEditorViewModel::onCancel
//            )
        }
    }

    CategoryExplorer(
        state = mappedState,
        modifier = Modifier.fillMaxSize(),
        onCategorySelected = viewModel::onCategorySelected,
        onConceptSelected = viewModel::onConceptSelected,
        onAddCard = viewModel::onAddCard
    )
}
