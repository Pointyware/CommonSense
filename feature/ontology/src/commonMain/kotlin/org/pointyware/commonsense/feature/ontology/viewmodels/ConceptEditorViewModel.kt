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
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.interactors.CreateNewConceptUseCase

/**
 * Maintains the state of a Concept Editor UI, reflected in [state].
 */
interface ConceptEditorViewModel {
    /**
     * Observable that reports the current state of the editor.
     */
    val state: StateFlow<ConceptEditorUiState>

    /**
     * Observable that reports when the editor is closed.
     */
    val onFinish: SharedFlow<Unit>

    /**
     * Prepare the editor for a new concept or an existing one.
     */
    fun prepareFor(concept: Concept?)

    /**
     * Update the state with a new name.
     */
    fun onNameChange(newName: String)

    /**
     * Update the state with a new description.
     */
    fun onDescriptionChange(newDescription: String)

    /**
     * Cancel the changes and close the editor.
     */
    fun onCancel()

    /**
     * Commit the changes and close the editor.
     */
    fun onConfirm()
}

class ConceptEditorViewModelImpl(
    private val createNewConceptUseCase: CreateNewConceptUseCase,
): ViewModel(), ConceptEditorViewModel {

    private val mutableState = MutableStateFlow(ConceptEditorUiState.Empty)
    override val state: StateFlow<ConceptEditorUiState> get() = mutableState.asStateFlow()

    private val mutableOnFinish = MutableSharedFlow<Unit>()
    override val onFinish: SharedFlow<Unit> = mutableOnFinish.asSharedFlow()

    override fun prepareFor(concept: Concept?) {
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

    override fun onNameChange(newName: String) {
        mutableState.update {
            it.copy(name = newName)
        }
    }

    override fun onDescriptionChange(newDescription: String) {
        mutableState.update {
            it.copy(description = newDescription)
        }
    }
    override fun onCancel() {
        viewModelScope.launch {
            mutableOnFinish.emit(Unit)
        }
    }

    override fun onConfirm() {
        viewModelScope.launch {
            val state = state.value
            createNewConceptUseCase.invoke(state.name, state.description)
            mutableOnFinish.emit(Unit)
        }
    }
}
