package org.pointyware.commonsense.core.navigation.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.commonsense.core.navigation.CymaticsNavController
import org.pointyware.commonsense.core.navigation.StackNavigationController

/**
 *
 */
interface NavigationDependencies {
    fun getNavController(): CymaticsNavController
}

class KoinNavigationDependencies: NavigationDependencies, KoinComponent {
    override fun getNavController(): CymaticsNavController = get()
}
