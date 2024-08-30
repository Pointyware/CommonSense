package org.pointyware.commonsense.shared.home.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.commonsense.feature.ontology.navigation.categoryExplorer
import org.pointyware.commonsense.feature.ontology.navigation.ontologyRoute
import org.pointyware.commonsense.shared.home.HomeUiStateMapper
import org.pointyware.commonsense.shared.home.HomeViewModel
import org.pointyware.commonsense.shared.home.HomeViewModelImpl

/**
 * Defines productions bindings to satisfy interface requests.
 */
fun homeModule() = module {
    single<HomeDependencies> { KoinHomeDependencies() }

    single<HomeUiStateMapper> { HomeUiStateMapper }
    single<HomeViewModel> { HomeViewModelImpl() }

    factory<Any>(qualifier = named("home")) { categoryExplorer }
}
