package org.pointyware.commonsense.feature.ontology.viewmodels

/**
 * UI-agnostic state of a category explorer.
 *
 * @param loading Whether the category explorer is currently loading data.
 * @param currentCategory The current category being viewed.
 * @param conceptEditor The state of the concept editor, if it is open.
 */
data class CategoryExplorerUiState(
    val loading: Boolean = false,
    val currentCategory: CategoryUiState,
    val editorState: CategoryExplorerEditorState = CategoryExplorerEditorState.Disabled,
) {
    companion object {
        val Loading = CategoryExplorerUiState(true, CategoryUiState())
    }
}

sealed interface CategoryExplorerEditorState {
    data object Disabled : CategoryExplorerEditorState
    data class Concept(
        val concept: ConceptEditorUiState
    ) : CategoryExplorerEditorState
    data class Category(
        val category: CategoryEditorUiState
    ) : CategoryExplorerEditorState
}
