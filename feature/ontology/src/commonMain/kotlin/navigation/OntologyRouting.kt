package org.pointyware.commonsense.feature.ontology.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.navigation.LocationRootScope
import org.pointyware.commonsense.core.navigation.StaticRoute
import org.pointyware.commonsense.feature.ontology.ConceptSpaceScreen
import org.pointyware.commonsense.feature.ontology.category.ui.CategoryExplorerScreen
import org.pointyware.commonsense.feature.ontology.category.viewmodels.CategoryEditorViewModel
import org.pointyware.commonsense.feature.ontology.category.viewmodels.CategoryExplorerViewModel
import org.pointyware.commonsense.feature.ontology.ui.CategoryCreator
import org.pointyware.commonsense.feature.ontology.ui.ConceptEditor
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptSpaceViewModel

val ontologyRoute = StaticRoute("ontology", Unit)
val categoryExplorer = ontologyRoute.fixed("categoryExplorer")
val categoryCreator = ontologyRoute.fixed("categoryCreator")
val conceptEditor = ontologyRoute.fixed("conceptEditor")

/**
 *
 */
@Composable
fun LocationRootScope<Any, Any>.ontologyRouting() {
    location(
        key = ontologyRoute,
    ) {

        Log.v("OntologyRouting")
        val koin = remember { getKoin() }
        val viewModel = remember { koin.get<ConceptSpaceViewModel>() }
        ConceptSpaceScreen(
            viewModel = viewModel,
        )
    }

    location(
        key = categoryExplorer,
    ) {
        Log.v("CategoryExplorer")

        val koin = remember { getKoin() }
        val viewModel = remember { koin.get<CategoryExplorerViewModel>() }

        CategoryExplorerScreen(
            viewModel = viewModel,
        )
    }

    location(
        key = categoryCreator
    ) {
        Log.v("CategoryCreator")
        val koin = remember { getKoin() }
        val viewModel = remember { koin.get<CategoryEditorViewModel>() }

        val state by viewModel.state.collectAsState()
        CategoryCreator(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onNameChange = viewModel::onNameChange,
            onCancel = viewModel::onCancel,
            onConfirm = viewModel::onConfirm,
        )
    }

    location(
        key = conceptEditor
    ) {

        Log.v("ConceptEditor")
        val koin = remember { getKoin() }
        val viewModel = remember { koin.get<ConceptEditorViewModel>() }

        val state by viewModel.editorState.collectAsState()
        ConceptEditor(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onNameChange = viewModel::onNameChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onCancel = viewModel::onCancel,
            onConfirm = viewModel::onCommitConcept,
        )
    }
}
