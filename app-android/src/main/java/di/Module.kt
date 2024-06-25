package org.pointyware.commonsense.android.di

import org.koin.dsl.module
import org.pointyware.commonsense.shared.entities.SharedFileResources
import org.pointyware.commonsense.shared.entities.SharedStringResources
import org.pointyware.commonsense.shared.ui.SharedDrawableResources
import org.pointyware.commonsense.shared.ui.SharedFontResources

/**
 *
 */
fun androidModule() = module {
    single<SharedStringResources> { AndroidStringResources() }
    single<SharedFontResources> { AndroidFontResources() }
    single<SharedDrawableResources> { AndroidDrawableResources() }
    single<SharedFileResources> { AndroidFileResources() }
}
