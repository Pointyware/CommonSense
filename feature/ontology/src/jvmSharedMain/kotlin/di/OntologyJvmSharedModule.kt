package org.pointyware.commonsense.feature.ontology.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.pointyware.commonsense.feature.ontology.local.ConceptSpaceDataSource
import org.pointyware.commonsense.feature.ontology.local.ConceptSpaceJsonDataSource

/**
 * Provides JVM-specific ontology dependencies
 */
fun ontologyJvmSharedModule() = module {
    single<ConceptSpaceDataSource> { ConceptSpaceJsonDataSource(get<Json>()) }
}
