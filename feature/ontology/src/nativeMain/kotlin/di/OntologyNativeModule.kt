package org.pointyware.commonsense.feature.ontology.di

import org.koin.dsl.module
import org.pointyware.commonsense.feature.ontology.interactors.NativeSelectFileUseCase
import org.pointyware.commonsense.ontology.interactors.SelectFileUseCase

/**
 *
 */
fun ontologyNativeModule() = module {
    single<SelectFileUseCase> { NativeSelectFileUseCase() }
}
