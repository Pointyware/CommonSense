package org.pointyware.commonsense.feature.ontology.category.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryEditorUiState

/**
 * Maintains the state of a Category Editor UI, reflected in [state].
 */
interface CategoryEditorViewModel {

    val state: StateFlow<CategoryEditorUiState>
}

/**
 */
class CategoryEditorViewModelImpl(

): CategoryEditorViewModel, ViewModel() {


    private val mutableState = MutableStateFlow(CategoryEditorUiState.Empty)
    override val state: StateFlow<CategoryEditorUiState>
        get() = TODO("Not yet implemented")
}
