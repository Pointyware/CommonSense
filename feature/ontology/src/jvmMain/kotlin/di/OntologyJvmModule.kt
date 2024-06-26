package org.pointyware.commonsense.ontology.di

import org.koin.dsl.module
import org.pointyware.commonsense.ontology.interactors.JvmSelectFileUseCase
import org.pointyware.commonsense.ontology.interactors.SelectFileUseCase

/**
 * Provides JVM-specific ontology dependencies
 */
fun ontologyJvmModule() = module {
    single<SelectFileUseCase> { JvmSelectFileUseCase() }
}
