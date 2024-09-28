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
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorViewModel

/**
 * Maintains the state of the category explorer.
 */
class CategoryExplorerViewModel(
    private val getSelectedCategoryUseCase: GetSelectedCategoryUseCase,
    private val getSelectedConceptUseCase: GetSelectedConceptUseCase,
    private val conceptEditorViewModel: ConceptEditorViewModel,
    private val categoryEditorViewModel: CategoryEditorViewModel
): ViewModel(), ConceptEditorViewModel by conceptEditorViewModel {

    /*
    TODO: remove implementation by delegation
      1. Simplify concept editor view model signatures
      2. Do not inherit from ConceptEditorViewModel or CategoryEditorViewModel
      3. Define concept/category-specific functions in this class
     */

    enum class EditorState {
        Disabled,
        Concept,
        Category
    }

    private val _loadingState = MutableStateFlow(false)
    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    private val _editorState = MutableStateFlow(EditorState.Disabled)

    val state: StateFlow<CategoryExplorerUiState> get() = combine(
        _loadingState, _categoryUiState, conceptEditorViewModel.editorState, _editorState
    ) { loading, currentCategory, conceptEditor, editorState ->
        CategoryExplorerUiState(
            loading = loading,
            currentCategory = currentCategory,
            conceptEditor = if (editorState == EditorState.Concept) conceptEditor else null
        ).also {
            Log.v("onChange state\n$it")
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
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
                    _editorState.value = EditorState.Concept
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
        _editorState.value = EditorState.Concept
    }

    fun onAddCategory() {
//        categoryEditorViewModel.prepareFor(null) // TODO: implement category editor
        _editorState.value = EditorState.Concept
    }

    init {
        viewModelScope.launch {
            conceptEditorViewModel.onFinish.collect {
                _editorState.value = EditorState.Disabled
            }
        }
    }
}
