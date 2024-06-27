package org.pointyware.commonsense.shared.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.pointyware.commonsense.core.data.di.dataModule
import org.pointyware.commonsense.core.entities.di.coreEntitiesModule
import org.pointyware.commonsense.core.interactors.di.coreInteractorsModule
import org.pointyware.commonsense.core.local.di.coreLocalModule
import org.pointyware.commonsense.core.navigation.di.coreNavigationModule
import org.pointyware.commonsense.core.remote.di.coreRemoteModule
import org.pointyware.commonsense.core.ui.di.coreUiModule
import org.pointyware.commonsense.core.viewmodels.di.coreViewModelsModule
import org.pointyware.commonsense.feature.ontology.di.ontologyModule
import org.pointyware.commonsense.shared.FileViewModel
import org.pointyware.commonsense.shared.home.di.homeModule


fun appModule(): Module = module {
    includes(
        coreModule(),
        featureModule(),
        homeModule(),
        sharedViewModelsModule()
    )
}

fun coreModule() = module {
    includes(
        coreEntitiesModule(),
        coreInteractorsModule(),
        coreViewModelsModule(),
        dataModule(),
        coreLocalModule(),
        coreRemoteModule(),
        coreNavigationModule(),
        coreUiModule(),
    )
}

fun featureModule() = module {
    includes(
        ontologyModule()
    )
}

fun sharedViewModelsModule() = module {
    factory<FileViewModel> { FileViewModel(get(), get(), get()) }
}
