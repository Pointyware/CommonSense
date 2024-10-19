package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.interactors.CreateNewCategoryUseCase
import kotlin.uuid.ExperimentalUuidApi

/**
 * Maintains the state of a Category Editor UI, reflected in [state].
 */
interface CategoryEditorViewModel {
    val state: StateFlow<CategoryEditorUiState>
    val onFinish: SharedFlow<Unit>
    fun prepareFor(category: Category?)
    fun onNameChange(newName: String)
    fun onConfirm()
    fun onCancel()
}

/**
 */
@OptIn(ExperimentalUuidApi::class)
class CategoryEditorViewModelImpl(
    private val createNewCategoryUseCase: CreateNewCategoryUseCase,
): CategoryEditorViewModel, ViewModel() {

    private val mutableState = MutableStateFlow(CategoryEditorUiState.Empty)
    override val state: StateFlow<CategoryEditorUiState>
        get() = mutableState.asStateFlow()

    private val mutableFinish = MutableSharedFlow<Unit>()
    override val onFinish: SharedFlow<Unit>
        get() = mutableFinish.asSharedFlow()

    override fun prepareFor(category: Category?) {
        mutableState.update {
            it.copy(name = category?.name ?: "")
        }
    }

    override fun onNameChange(newName: String) {
        mutableState.update {
            it.copy(name = newName)
        }
    }

    override fun onConfirm() {
        viewModelScope.launch {
            val state = state.value
            createNewCategoryUseCase.invoke(state.name)
            mutableFinish.emit(Unit)
        }
    }

    override fun onCancel() {
        viewModelScope.launch {
            mutableFinish.emit(Unit)
        }
    }
}
