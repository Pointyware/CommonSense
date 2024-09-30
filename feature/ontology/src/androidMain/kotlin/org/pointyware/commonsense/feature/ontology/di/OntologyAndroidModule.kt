package org.pointyware.commonsense.feature.ontology.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.commonsense.feature.ontology.local.DriverFactory

/**
 *
 */
fun ontologyLocalAndroidModule() = module {
    singleOf(::DriverFactory)
}
