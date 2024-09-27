package org.pointyware.commonsense.feature.ontology.test

import org.koin.core.context.startKoin
import org.pointyware.commonsense.core.data.di.dataModule
import org.pointyware.commonsense.core.entities.di.coreEntitiesModule
import org.pointyware.commonsense.core.interactors.di.coreInteractorsModule
import org.pointyware.commonsense.core.local.di.coreLocalModule
import org.pointyware.commonsense.core.navigation.di.coreNavigationModule
import org.pointyware.commonsense.core.remote.di.coreRemoteModule
import org.pointyware.commonsense.core.ui.di.coreUiModule
import org.pointyware.commonsense.core.viewmodels.di.coreViewModelsModule
import org.pointyware.commonsense.feature.ontology.di.ontologyModule

/**
 *
 */
fun setupKoin() {
    startKoin {
        modules(
            coreEntitiesModule(),
            dataModule(),
            coreRemoteModule(),
            coreLocalModule(),
            coreNavigationModule(),
            coreInteractorsModule(),
            coreViewModelsModule(),
            coreUiModule(),

            ontologyModule()
        )
    }
}
