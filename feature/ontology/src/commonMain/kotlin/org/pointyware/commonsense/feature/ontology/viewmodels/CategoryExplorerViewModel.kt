package org.pointyware.commonsense.feature.ontology.viewmodels

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
import org.pointyware.commonsense.feature.ontology.interactors.GetSelectedCategoryUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetSelectedConceptUseCase

/**
 * Maintains the state of the category explorer.
 */
class CategoryExplorerViewModel(
    private val getSelectedCategoryUseCase: GetSelectedCategoryUseCase,
    private val getSelectedConceptUseCase: GetSelectedConceptUseCase,
    private val conceptEditorViewModel: ConceptEditorViewModel,
    private val categoryEditorViewModel: CategoryEditorViewModel
): ViewModel() {

    enum class EditorState {
        Disabled,
        Concept,
        Category
    }

    private val _loadingState = MutableStateFlow(false)
    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    private val _editorState = MutableStateFlow(EditorState.Disabled)

    val state: StateFlow<CategoryExplorerUiState> get() = combine(
        _loadingState, _categoryUiState, conceptEditorViewModel.state, categoryEditorViewModel.state, _editorState
    ) { loading, currentCategory, conceptEditor, categoryEditor, editorState ->
        CategoryExplorerUiState(
            loading = loading,
            currentCategory = currentCategory,
            editorState = when (editorState) {
                EditorState.Concept -> CategoryExplorerEditorState.Concept(conceptEditor)
                EditorState.Category -> CategoryExplorerEditorState.Category(categoryEditor)
                EditorState.Disabled -> CategoryExplorerEditorState.Disabled
            },
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

    fun onCategoryNameChange(name: String) {
        categoryEditorViewModel.onNameChange(name)
    }

    fun onCommitCategory() {
        categoryEditorViewModel.onConfirm()
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

    fun onConceptNameChange(name: String) {
        conceptEditorViewModel.onNameChange(name)
    }

    fun onDescriptionChange(description: String) {
        conceptEditorViewModel.onDescriptionChange(description)
    }

    fun onCommitConcept() {
        conceptEditorViewModel.onConfirm()
    }

    fun onCancel() {
        _editorState.value = EditorState.Disabled
    }

    fun onAddCard() {
        conceptEditorViewModel.prepareFor(null)
        _editorState.value = EditorState.Concept
    }

    fun onAddCategory() {
        categoryEditorViewModel.prepareFor(null)
        _editorState.value = EditorState.Category
    }

    init {
        viewModelScope.launch {
            conceptEditorViewModel.onFinish.collect {
                _editorState.value = EditorState.Disabled
            }
        }
        viewModelScope.launch {
            categoryEditorViewModel.onFinish.collect {
                _editorState.value = EditorState.Disabled
            }
        }
        onCategorySelected(Uuid.nil())
    }
}
