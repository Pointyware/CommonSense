package org.pointyware.commonsense.core.navigation.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.commonsense.core.navigation.StackNavigationController
import org.pointyware.commonsense.core.navigation.StackNavigationControllerImpl

/**
 *
 */
fun coreNavigationModule() = module {
    single<NavigationDependencies> { KoinNavigationDependencies() }

    single<StackNavigationController<Any, Any>> {
        StackNavigationControllerImpl(get<Any>(qualifier = named("home")))
    }
}
