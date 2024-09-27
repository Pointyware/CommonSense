package org.pointyware.commonsense.feature.ontology.category.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.category.interactors.GetSelectedCategoryUseCase
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorUiState
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorViewModel

/**
 * Maintains the state of the category explorer.
 */
class CategoryExplorerViewModel(
    private val getSelectedCategoryUseCase: GetSelectedCategoryUseCase,
    private val conceptEditorViewModel: ConceptEditorViewModel,
): ViewModel() {

    private val _loadingState = MutableStateFlow(false)
    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    private val _conceptEditorUiState = MutableStateFlow<ConceptEditorUiState?>(null)
    private val _conceptEditorEnabled = MutableStateFlow(false)

    val state: StateFlow<CategoryExplorerUiState> get() = combine(
        _loadingState, _categoryUiState, _conceptEditorUiState, _conceptEditorEnabled
    ) { loading, currentCategory, conceptEditor, conceptEditorEnabled ->
        CategoryExplorerUiState(
            loading = loading,
            currentCategory = currentCategory,
            conceptEditor = if (conceptEditorEnabled) conceptEditor else null
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        CategoryExplorerUiState.Loading,
    )

    fun onCategorySelected(categoryId: Uuid) {
        _loadingState.value = true

        viewModelScope.launch {
            getSelectedCategoryUseCase.invoke(categoryId)
                .onSuccess { info ->
                    _categoryUiState.update {
                        CategoryUiState(
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
        _loadingState.value = true


    }

    fun onAddCard() {
        conceptEditorViewModel.prepareFor(null)
        _conceptEditorEnabled.value = true
    }
}
