package org.pointyware.commonsense.feature.ontology.di

import org.koin.dsl.module
import org.pointyware.commonsense.feature.ontology.interactors.JvmSelectFileUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SelectFileUseCase

/**
 * Provides JVM-specific ontology dependencies
 */
fun ontologyJvmModule() = module {
    single<SelectFileUseCase> { JvmSelectFileUseCase() }
}
