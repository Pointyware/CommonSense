package org.pointyware.commonsense.feature.ontology.category.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.category.interactors.GetSelectedCategoryUseCase
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorViewModel

/**
 * Maintains the state of the category explorer.
 */
class CategoryExplorerViewModel(
    private val getSelectedCategoryUseCase: GetSelectedCategoryUseCase,
    private val conceptEditorViewModel: ConceptEditorViewModel,
): ViewModel() {

    private val mutableState = MutableStateFlow(CategoryExplorerUiState.Loading)
    val state: StateFlow<CategoryExplorerUiState> get() = mutableState

    fun onCategorySelected(categoryId: Uuid) {
        mutableState.value = CategoryExplorerUiState.Loading

        viewModelScope.launch {
            getSelectedCategoryUseCase.invoke(categoryId)
                .onSuccess { info ->
                    mutableState.update {
                        CategoryExplorerUiState(
                            selected = info.subject,
                            subcategories = info.subcategories,
                            concepts = info.concepts
                        )
                    }
                }
                .onFailure {
                    println("Failed to get category info for id $categoryId")
                    it.printStackTrace()
                }
        }
    }

    fun onConceptSelected(conceptId: Uuid) {
        mutableState.value = CategoryExplorerUiState.Loading

        TODO("Open Concept Viewer with selected id")
    }

    fun getConceptEditorViewModel(concept: Concept?): ConceptEditorViewModel {
        conceptEditorViewModel.prepareFor(concept)
        return conceptEditorViewModel
    }
}
