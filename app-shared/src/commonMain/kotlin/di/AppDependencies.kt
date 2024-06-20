package org.pointyware.commonsense.shared.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.commonsense.core.navigation.di.NavigationDependencies

/**
 * Top level dependencies for the app
 */
interface AppDependencies {
    fun getNavigationDependencies(): NavigationDependencies
}

fun getDependencies(): AppDependencies = KoinAppDependencies()

class KoinAppDependencies: AppDependencies, KoinComponent {
    override fun getNavigationDependencies(): NavigationDependencies = get()
}
