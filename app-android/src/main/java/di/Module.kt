package org.pointyware.commonsense.android.di

import org.koin.dsl.module
import org.pointyware.commonsense.shared.entities.SharedStringResources

/**
 *
 */
fun androidModule() = module {
    single<SharedStringResources> { AndroidStringResources() }
}
