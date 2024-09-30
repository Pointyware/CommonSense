package org.pointyware.commonsense.feature.ontology.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.commonsense.feature.ontology.interactors.JvmSelectFileUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SelectFileUseCase
import org.pointyware.commonsense.feature.ontology.local.DriverFactory

/**
 * Provides JVM-specific ontology dependencies
 */
fun ontologyLocalJvmModule() = module {
    single<SelectFileUseCase> { JvmSelectFileUseCase() }

    singleOf(::DriverFactory)
}
