@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.data.RecordsRepository
import org.pointyware.commonsense.feature.ontology.interactors.GetSelectedCategoryUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetSelectedInstanceUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Maintains the state of the category explorer.
 */
@OptIn(ExperimentalUuidApi::class)
class CategoryExplorerViewModel(
    private val getSelectedCategoryUseCase: GetSelectedCategoryUseCase,
    private val getSelectedInstanceUseCase: GetSelectedInstanceUseCase,
    private val recordEditorViewModel: RecordEditorViewModel,
    private val instanceEditorViewModel: InstanceEditorViewModel,
    private val categoryEditorViewModel: CategoryEditorViewModel,
    private val recordsRepository: RecordsRepository,
): ViewModel() {

    enum class EditorState {
        /**
         * No editor is active.
         */
        Disabled,
        /**
         * The record editor is active.
         */
        Record,
        /**
         * The instance editor is active.
         */
        Instance,
        /**
         * The category editor is active.
         */
        Category
    }

    private val _loadingState = MutableStateFlow(false)
    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    private val _editorState = MutableStateFlow(EditorState.Disabled)

    val state: StateFlow<CategoryExplorerUiState> get() = combine(
        _loadingState, _categoryUiState, recordEditorViewModel.state, instanceEditorViewModel.state, categoryEditorViewModel.state, _editorState
    ) { stateArray ->
        val loading = stateArray[0] as Boolean
        val currentCategory = stateArray[1] as CategoryUiState
        val recordEditor = stateArray[2] as RecordEditorUiState
        val instanceEditor = stateArray[3] as InstanceEditorUiState?
        val categoryEditor = stateArray[4] as CategoryEditorUiState
        val editorState = stateArray[5] as EditorState

        CategoryExplorerUiState(
            loading = loading,
            currentCategory = currentCategory,
            editorState = when (editorState) {
                EditorState.Record -> CategoryExplorerEditorState.Record(recordEditor)
                EditorState.Instance -> instanceEditor?.let { CategoryExplorerEditorState.Instance(instanceEditor) } ?: CategoryExplorerEditorState.Disabled
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
                    _loadingState.value = false
                    _categoryUiState.update {
                        CategoryUiState(
                            selected = info.subject.toUiState(),
                            subcategories = info.subcategories.map(Category::toUiState),
                            concepts = emptyList(),
                            instances = info.instances.map(Value.Instance::toUiState)
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
            getSelectedInstanceUseCase.invoke(categoryId = category.id, instanceId = conceptId)
                .onSuccess {
                    // TODO: replace with record version
//                    recordEditorViewModel.prepareFor(it)
                    _editorState.value = EditorState.Record
                }
                .onFailure {
                    // TODO: post error to user
                    Log.v("Failed to get concept info for id $conceptId")
                    it.printStackTrace()
                }
        }
    }

    fun onConfirmRecord() {
        recordEditorViewModel.onConfirm()
    }

    fun onCancelEditor() {
        _editorState.value = EditorState.Disabled
    }

    fun onAddCard() {
        recordEditorViewModel.prepareFor(null)
        _editorState.value = EditorState.Record
    }

    fun onAddStructure() {
        TODO("Open Record creation state")
    }

    fun onAddCategory() {
        categoryEditorViewModel.prepareFor(null)
        _editorState.value = EditorState.Category
    }

    fun onDeleteSelected(concepts: Set<Uuid>, categories: Set<Uuid>) {
        viewModelScope.launch {
            _loadingState.value = true

            // TODO: replace with remove categories interactor
            // TODO: replace with remove instances interactor
            reloadCurrentCategory()
            _loadingState.value = false // TODO: maintain loading state as list of pending operations
        }
    }

    private fun reloadCurrentCategory() {
        onCategorySelected(_categoryUiState.value.selected?.id ?: Uuid.NIL)
    }

    fun onRecordNameChange(newName: String) {
        recordEditorViewModel.onRecordNameChange(newName)
    }

    fun addField() {
        recordEditorViewModel.onFieldAdded()
    }

    fun setFieldName(index: Int, newName: String) {
        recordEditorViewModel.onFieldNameChange(index, newName)
    }

    fun setFieldType(index: Int, type: Type) {
        recordEditorViewModel.onFieldTypeChange(index, type)
    }

    fun setFieldValue(index: Int, value: Value<*>) {
        recordEditorViewModel.onFieldValueChange(index, value)
    }

    fun removeField(index: Int) {
        recordEditorViewModel.onFieldRemoved(index)
    }

    init {
        viewModelScope.launch {
            recordEditorViewModel.onFinish.collect {
                _editorState.value = EditorState.Disabled
            }
        }
        viewModelScope.launch {
            categoryEditorViewModel.onFinish.collect {
                _editorState.value = EditorState.Disabled
            }
        }
        onCategorySelected(Uuid.NIL)
    }
}
