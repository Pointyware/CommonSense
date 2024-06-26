package org.pointyware.commonsense.feature.ontology.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.navigation.LocationRootScope
import org.pointyware.commonsense.core.navigation.StaticRoute
import org.pointyware.commonsense.feature.ontology.ConceptSpaceScreen
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptSpaceViewModel

val ontologyRoute = StaticRoute("ontology", Unit)

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
}
