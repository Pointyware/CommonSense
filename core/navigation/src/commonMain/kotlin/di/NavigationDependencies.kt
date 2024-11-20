package org.pointyware.commonsense.core.navigation.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.commonsense.core.navigation.CommonSenseNavController

/**
 *
 */
interface NavigationDependencies {
    fun getNavController(): CommonSenseNavController
}

class KoinNavigationDependencies: NavigationDependencies, KoinComponent {
    override fun getNavController(): CommonSenseNavController = get()
}
