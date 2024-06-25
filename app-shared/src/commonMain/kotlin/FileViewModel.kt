package org.pointyware.commonsense.shared

import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.ontology.interactors.LoadConceptSpaceUseCase
import org.pointyware.commonsense.ontology.interactors.SaveConceptSpaceUseCase
import org.pointyware.commonsense.ontology.interactors.SelectFileUseCase

/**
 * Manages file operations for the application.
 */
class FileViewModel(
    private val selectFileUseCase: SelectFileUseCase,
    private val saveConceptSpaceUseCase: SaveConceptSpaceUseCase,
    private val loadConceptSpaceUseCase: LoadConceptSpaceUseCase,
): ViewModel() {
    fun save() {
        viewModelScope.launch {
            saveConceptSpaceUseCase()
        }
    }

    fun saveAs() {
        viewModelScope.launch {
            saveConceptSpaceUseCase(selectFile = true)
        }
    }

    fun load() {
        viewModelScope.launch {
            selectFileUseCase()
                .onSuccess {
                    loadConceptSpaceUseCase(it)
                }
                .onFailure { error ->
                    Log.e("Failed to load concept space", error)
                }
        }
    }
}
