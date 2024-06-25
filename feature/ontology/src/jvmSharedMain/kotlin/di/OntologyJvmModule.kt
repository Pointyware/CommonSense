package org.pointyware.commonsense.ontology.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.pointyware.commonsense.ontology.local.ConceptSpaceDataSource
import org.pointyware.commonsense.ontology.local.ConceptSpaceJsonDataSource
import java.io.File

/**
 * Provides JVM-specific ontology dependencies
 */
fun ontologyJvmModule() = module {
    single<ConceptSpaceDataSource> { ConceptSpaceJsonDataSource(get<Json>()) }
}
