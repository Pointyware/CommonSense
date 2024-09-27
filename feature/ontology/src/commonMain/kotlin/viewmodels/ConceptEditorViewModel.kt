package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.navigation.CymaticsNavController
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.category.interactors.CreateNewConceptUseCase

/**
 *
 */
class ConceptEditorViewModel(
    private val createNewConceptUseCase: CreateNewConceptUseCase,
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
            createNewConceptUseCase.invoke(state.name, state.description)
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

    fun prepareFor(concept: Concept?) {
        mutableState.value = concept?.let {
            ConceptEditorUiState(
                id = it.id,
                name = it.name,
                description = it.description ?: ""
            )
        } ?: ConceptEditorUiState(
            null, "", ""
        )
    }
}
