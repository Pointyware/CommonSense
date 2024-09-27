package org.pointyware.commonsense.feature.ontology.category.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.category.interactors.GetSelectedCategoryUseCase
import org.pointyware.commonsense.feature.ontology.category.interactors.GetSelectedConceptUseCase
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorUiState
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorViewModel

/**
 * Maintains the state of the category explorer.
 */
class CategoryExplorerViewModel(
    private val getSelectedCategoryUseCase: GetSelectedCategoryUseCase,
    private val getSelectedConceptUseCase: GetSelectedConceptUseCase,
    private val conceptEditorViewModel: ConceptEditorViewModel,
): ViewModel(), ConceptEditorViewModel by conceptEditorViewModel {

    private val _loadingState = MutableStateFlow(false)
    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    private val _conceptEditorEnabled = MutableStateFlow(false)

    val state: StateFlow<CategoryExplorerUiState> get() = combine(
        _loadingState, _categoryUiState, conceptEditorViewModel.editorState, _conceptEditorEnabled
    ) { loading, currentCategory, conceptEditor, conceptEditorEnabled ->
        CategoryExplorerUiState(
            loading = loading,
            currentCategory = currentCategory,
            conceptEditor = if (conceptEditorEnabled) conceptEditor else null
        ).also {
            Log.v("onChange state\n$it")
        }
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

                    Log.v("Failed to get category info for id $categoryId")
                    it.printStackTrace()
                }
        }
    }

    fun onConceptSelected(conceptId: Uuid) {
        _loadingState.value = true
        viewModelScope.launch {
            val category = _categoryUiState.value.selected ?: return@launch
            getSelectedConceptUseCase.invoke(categoryId = category.id, conceptId = conceptId)
                .onSuccess {
                    conceptEditorViewModel.prepareFor(it)
                    _conceptEditorEnabled.value = true
                }
                .onFailure {
                    // TODO: post error to user
                    Log.v("Failed to get concept info for id $conceptId")
                    it.printStackTrace()
                }
        }
    }

    fun onAddCard() {
        conceptEditorViewModel.prepareFor(null)
        _conceptEditorEnabled.value = true
    }

    fun onAddCategory() {

    }

    init {
        viewModelScope.launch {
            conceptEditorViewModel.onFinish.collect {
                Log.v("finish")
                _conceptEditorEnabled.value = false
            }
        }
        viewModelScope.launch {
            _conceptEditorEnabled.collect {
                Log.v("conceptEditorEnabled: $it")
            }
        }
    }
}
