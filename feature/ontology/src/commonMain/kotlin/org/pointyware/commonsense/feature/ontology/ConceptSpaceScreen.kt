@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.feature.ontology.space.ui.ConceptSpaceUiStateMapper
import org.pointyware.commonsense.feature.ontology.space.ui.ConceptSpaceView
import org.pointyware.commonsense.feature.ontology.space.viewmodels.ConceptSpaceViewModel
import kotlin.uuid.ExperimentalUuidApi

/**
 * Takes a view model and binds the state and events to the view.
 */
@Composable
fun ConceptSpaceScreen(
    viewModel: ConceptSpaceViewModel,
    modifier: Modifier = Modifier,
) {
    Log.v("ConceptSpaceScreen")
    val uiState = viewModel.state.collectAsState()
    ConceptSpaceView(
        state = ConceptSpaceUiStateMapper.map(uiState.value),
        modifier = modifier.fillMaxSize(),
        onDeleteNode = viewModel::onDeleteNode,
        onModifyNode = viewModel::onModifyNode,
        onCreateNode = viewModel::onCreateNode,
        onCompleteNode = viewModel::onCompleteNode,
    )
}
