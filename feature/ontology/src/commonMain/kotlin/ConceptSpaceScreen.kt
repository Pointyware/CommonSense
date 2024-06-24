package org.pointyware.commonsense.ontology

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.ontology.ui.ConceptSpaceUiStateMapper
import org.pointyware.commonsense.ontology.ui.ConceptSpaceView
import org.pointyware.commonsense.ontology.viewmodels.ConceptSpaceViewModel

/**
 * Takes a view model and binds the state and events to the view.
 */
@Composable
fun ConceptSpaceScreen(
    viewModel: ConceptSpaceViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state.collectAsState()
    ConceptSpaceView(
        state = ConceptSpaceUiStateMapper.map(state.value),
        modifier = modifier.fillMaxSize(),
        onDeleteNode = viewModel::onDeleteNode,
        onModifyNode = viewModel::onModifyNode,
        onCreateNode = viewModel::onCreateNode,
    )
}
