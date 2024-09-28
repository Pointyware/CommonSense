package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.navigation.CymaticsNavController
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.category.interactors.CreateNewCategoryUseCase

/**
 *
 */
class CategoryCreatorViewModel(
    private val createNewCategoryUseCase: CreateNewCategoryUseCase,
    private val navigationController: CymaticsNavController,
): ViewModel() {

    private val mutableState = MutableStateFlow(ConceptEditorUiState.Empty)
    val state: StateFlow<ConceptEditorUiState> get() = mutableState.asStateFlow()


    fun onCancel() {
        navigationController.goBack()
    }

    fun onConfirm() {
        viewModelScope.launch {
            val state = state.value
            createNewCategoryUseCase.invoke(state.name)
            navigationController.goBack()
        }
    }

    fun onNameChange(newName: String) {
        mutableState.update {
            it.copy(name = newName)
        }
    }

    fun onDescriptionChange(newDescription: String) {
        mutableState.update {
            it.copy(description = newDescription)
        }
    }
}
