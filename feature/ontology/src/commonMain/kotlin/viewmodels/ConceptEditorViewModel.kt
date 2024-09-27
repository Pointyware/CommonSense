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
import org.pointyware.commonsense.feature.ontology.category.interactors.CreateNewConceptUseCase

/**
 *
 */
interface ConceptEditorViewModel {
    val editorState: StateFlow<ConceptEditorUiState>
    val onFinish: SharedFlow<Unit>
    fun onCancel()
    fun onConfirm()
    fun onNameChange(newName: String)
    fun onDescriptionChange(newDescription: String)
    fun prepareFor(concept: Concept?)
}

/**
 *
 */
class ConceptEditorViewModelImpl(
    private val createNewConceptUseCase: CreateNewConceptUseCase,
): ViewModel(), ConceptEditorViewModel {

    private val mutableState = MutableStateFlow(ConceptEditorUiState.Empty)
    override val editorState: StateFlow<ConceptEditorUiState> get() = mutableState.asStateFlow()

    private val mutableOnFinish = MutableSharedFlow<Unit>()
    override val onFinish: SharedFlow<Unit> = mutableOnFinish.asSharedFlow()

    override fun onCancel() {
        mutableOnFinish.tryEmit(Unit)
    }

    override fun onConfirm() {
        viewModelScope.launch {
            val state = editorState.value
            createNewConceptUseCase.invoke(state.name, state.description)
            mutableOnFinish.emit(Unit)
        }
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
}
