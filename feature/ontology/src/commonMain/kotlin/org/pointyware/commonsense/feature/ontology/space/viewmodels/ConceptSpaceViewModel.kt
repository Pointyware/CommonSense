@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.space.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.io.files.Path
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.viewmodels.ViewModel
import org.pointyware.commonsense.feature.ontology.data.ArrangementController
import org.pointyware.commonsense.feature.ontology.data.Position
import org.pointyware.commonsense.feature.ontology.interactors.AddNewNodeUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetActiveConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.LoadConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.RemoveNodeUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SaveConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.UpdateNodeUseCase
import org.pointyware.commonsense.feature.ontology.local.generateRandomId
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
class ConceptSpaceViewModel(
    private val getActiveConceptSpaceUseCase: GetActiveConceptSpaceUseCase,
    private val loadConceptSpaceUseCase: LoadConceptSpaceUseCase,
    private val saveConceptSpaceUseCase: SaveConceptSpaceUseCase,
    private val addNewNodeUseCase: AddNewNodeUseCase,
    private val updateNodeUseCase: UpdateNodeUseCase,
    private val removeNodeUseCase: RemoveNodeUseCase,
    private val arrangementController: ArrangementController
): ViewModel() {

    init {
        Log.v("ConceptSpaceViewModel created")
    }

    private val emptySpace = ConceptSpaceUiState(
        OntologyUiState(
            id = generateRandomId(),
            nodes = emptyList(),
            edges = emptyList()
        )
    )

    val state: StateFlow<ConceptSpaceUiState> = combine(
        getActiveConceptSpaceUseCase(),
        arrangementController.frozenIds
    ) { conceptSpace, frozenIds ->
        Log.v("Mapping concept space: $conceptSpace")
        ConceptSpaceUiState(
            OntologyUiState(
                id = conceptSpace.id,
                nodes = conceptSpace.focus.instances.map { instance ->
                    val position = arrangementController.getConceptPositionOrPut(instance, 0f, 0f)
                    InfoNodeUiState(
                        instance.id,
                        instance.toString(), // TODO: replace with description provider
                        instance.id in frozenIds,
                        position.x,
                        position.y
                    )
                },
                edges = conceptSpace.focus.relations.map { relation ->
                    InfoEdgeUiState(
                        relation.id,
                        relation.type,
                        relation.source.id,
                        relation.target.id
                    )
                }
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptySpace
    )

    fun onLoadConceptSpace(file: Path) {
        viewModelScope.launch {
            loadConceptSpaceUseCase(file)
                .onSuccess { conceptSpace ->
                    Log.v("Loaded concept space: $conceptSpace")
                }
                .onFailure { error ->
                    Log.v("Failed to load concept space: $error")
                }
        }
    }

    fun onSaveConceptSpace(selectFile: Boolean = false) {
        viewModelScope.launch {
            saveConceptSpaceUseCase(selectFile)
        }
    }

    fun onDeleteNode(id: Uuid) {
        viewModelScope.launch {
            removeNodeUseCase(id)
        }
    }

    fun onModifyNode(id: Uuid) {
        viewModelScope.launch {

            arrangementController.freeze(id)
        }
    }

    fun onCreateNode(x: Float, y: Float) {
        viewModelScope.launch {
            addNewNodeUseCase(x, y)
            mutablePointSet.update {
                it + Position(x, y)
            }
        }
    }

    fun onCompleteNode(id: Uuid, newValue: String) {
        viewModelScope.launch {
            updateNodeUseCase(id, newValue)
            arrangementController.unfreeze(id)
        }
    }

    private val mutablePointSet = MutableStateFlow<Set<Position>>(emptySet())
    val pointSet: StateFlow<Set<Position>> get() = mutablePointSet
}
