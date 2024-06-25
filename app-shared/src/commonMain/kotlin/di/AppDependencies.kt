package org.pointyware.commonsense.shared.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.commonsense.core.navigation.di.NavigationDependencies
import org.pointyware.commonsense.shared.FileViewModel
import org.pointyware.commonsense.shared.entities.SharedFileResources
import org.pointyware.commonsense.shared.entities.SharedStringResources
import org.pointyware.commonsense.shared.ui.SharedDrawableResources
import org.pointyware.commonsense.shared.ui.SharedFontResources

/**
 * Top level dependencies for the app
 */
interface AppDependencies {
    fun getNavigationDependencies(): NavigationDependencies
    fun getStringResources(): SharedStringResources
    fun getFontResources(): SharedFontResources
    fun getDrawableResources(): SharedDrawableResources
    fun getFileResources(): SharedFileResources
    fun getFileViewModel(): FileViewModel
}

fun getDependencies(): AppDependencies = KoinAppDependencies()

class KoinAppDependencies: AppDependencies, KoinComponent {
    override fun getNavigationDependencies(): NavigationDependencies = get()
    override fun getStringResources(): SharedStringResources = get()
    override fun getFontResources(): SharedFontResources = get()
    override fun getDrawableResources(): SharedDrawableResources = get()
    override fun getFileResources(): SharedFileResources = get()
    override fun getFileViewModel(): FileViewModel = get()
}
