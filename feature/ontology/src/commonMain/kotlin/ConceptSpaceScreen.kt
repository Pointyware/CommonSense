package org.pointyware.commonsense.ontology

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.core.common.Log
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
    Log.v("ConceptSpaceScreen")
    val uiState = viewModel.state.collectAsState()
//    val pointSet = viewModel.pointSet.collectAsState()
    ConceptSpaceView(
        state = ConceptSpaceUiStateMapper.map(uiState.value),
//        state = ConceptSpaceViewState(
//            errorMessage = null,
//            infoEdges = emptyList(),
//            infoNodes = pointSet.value.mapIndexed { index, it ->
//                val title = remember(it) {
//                    "${it.x},${it.y}"
//                }
//                InfoNodeState(
//                    id = index.toString(),
//                    title = title,
//                    x = it.x.dp,
//                    y = it.y.dp,
//                )
//            }
//        ),
        modifier = modifier.fillMaxSize(),
        onDeleteNode = viewModel::onDeleteNode,
        onModifyNode = viewModel::onModifyNode,
        onCreateNode = viewModel::onCreateNode,
    )
}
