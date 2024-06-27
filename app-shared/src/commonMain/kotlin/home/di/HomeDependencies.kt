package org.pointyware.commonsense.shared.home.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.commonsense.shared.home.HomeUiStateMapper
import org.pointyware.commonsense.shared.home.HomeViewModel

/**
 * Defines dependencies needed by all Home locations
 */
interface HomeDependencies {
    fun getHomeViewModel(): HomeViewModel
    fun getHomeUiStateMapper(): HomeUiStateMapper
}

class KoinHomeDependencies: HomeDependencies, KoinComponent {
    override fun getHomeViewModel(): HomeViewModel = get()
    override fun getHomeUiStateMapper(): HomeUiStateMapper = get()
}
