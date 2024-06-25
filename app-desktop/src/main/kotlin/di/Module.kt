package org.pointyware.commonsense.desktop.di

import org.koin.dsl.module
import org.pointyware.commonsense.ontology.local.di.ontologyJvmModule
import org.pointyware.commonsense.shared.entities.SharedFileResources
import org.pointyware.commonsense.shared.entities.SharedStringResources
import org.pointyware.commonsense.shared.ui.SharedDrawableResources
import org.pointyware.commonsense.shared.ui.SharedFontResources

/**
 * Provides the desktop module
 */
fun desktopModule() = module {
    single<SharedStringResources> { DesktopStringResources() }
    single<SharedFontResources> { DesktopFontResources() }
    single<SharedDrawableResources> { DesktopDrawableResources() }
    single<SharedFileResources> { DesktopFileResources() }

    includes(
        ontologyJvmModule(),
        desktopResourcesModule(),
        desktopViewModelsModule()
    )
}

fun desktopResourcesModule() = module {
    single<SharedStringResources> { DesktopStringResources() }
    single<SharedFontResources> { DesktopFontResources() }
    single<SharedDrawableResources> { DesktopDrawableResources() }
    single<SharedFileResources> { DesktopFileResources() }
}

fun desktopViewModelsModule() = module {

}
