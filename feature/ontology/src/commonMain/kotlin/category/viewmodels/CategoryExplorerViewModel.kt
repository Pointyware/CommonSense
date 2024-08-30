package org.pointyware.commonsense.feature.ontology.category.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.entities.Category

/**
 * Maintains the state of the category explorer.
 */
class CategoryExplorerViewModel(

): ViewModel() {

    // TODO: replace with state watching/mapping use case
    private val mutableState = MutableStateFlow(CategoryExplorerUiState.Loading)
    val state: StateFlow<CategoryExplorerUiState> get() = mutableState

    init {
        mutableState.value = CategoryExplorerUiState(
            selected = Category(
                id = Uuid.v4(),
                name = "Test",
            ),
            subcategories = emptyList(),
            concepts = emptyList(),
            loading = false
        )
    }

    fun onCategorySelected(categoryId: Uuid) {
        mutableState.value = CategoryExplorerUiState.Loading

        TODO("Initiate loading of subcategories and concepts")
    }

    fun onConceptSelected(conceptId: Uuid) {
        mutableState.value = CategoryExplorerUiState.Loading

        TODO("Open Concept Viewer with selected id")
    }
}
