package org.pointyware.commonsense.feature.ontology.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.commonsense.feature.ontology.interactors.NativeSelectFileUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SelectFileUseCase
import org.pointyware.commonsense.feature.ontology.local.DriverFactory

/**
 *
 */
fun ontologyNativeModule() = module {
    single<SelectFileUseCase> { NativeSelectFileUseCase() }

    singleOf(::DriverFactory)
}
