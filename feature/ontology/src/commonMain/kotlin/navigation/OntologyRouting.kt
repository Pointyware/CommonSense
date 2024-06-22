package org.pointyware.commonsense.ontology.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.navigation.LocationRootScope
import org.pointyware.commonsense.core.navigation.StaticRoute
import org.pointyware.commonsense.ontology.ConceptSpaceScreen
import org.pointyware.commonsense.ontology.viewmodels.ConceptSpaceViewModel

val ontologyRoute = StaticRoute("ontology", Unit)

/**
 *
 */
@Composable
fun LocationRootScope<Any, Any>.ontologyRouting() {
    location(
        key = ontologyRoute,
    ) {

        val koin = remember { getKoin() }
        val viewModel = remember { koin.get<ConceptSpaceViewModel>() }
        ConceptSpaceScreen(
            viewModel = viewModel,
        )
    }
}
