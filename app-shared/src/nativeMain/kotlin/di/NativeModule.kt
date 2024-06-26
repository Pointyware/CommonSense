package org.pointyware.commonsense.feature.ontology.di

import org.koin.dsl.module

/**
 *
 */
fun nativeModule() = module {
    includes(
        ontologyNativeModule(),
    )
}
