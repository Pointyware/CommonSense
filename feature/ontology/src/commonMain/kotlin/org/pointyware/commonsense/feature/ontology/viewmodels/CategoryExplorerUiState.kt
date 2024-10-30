package org.pointyware.commonsense.feature.ontology.viewmodels

/**
 * UI-agnostic state of a category explorer.
 *
 * @param loading Whether the category explorer is currently loading data.
 * @param currentCategory The current category being viewed.
 * @param editorState The state of the concept editor, if it is open.
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
    data class Record(
        val record: RecordEditorUiState
    ): CategoryExplorerEditorState
    data class Instance(
        val instance: InstanceEditorUiState
    )
    data class Category(
        val category: CategoryEditorUiState
    ) : CategoryExplorerEditorState
}
