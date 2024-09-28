package org.pointyware.commonsense.android.di

import android.app.Application
import android.content.Context
import org.koin.dsl.module
import org.pointyware.commonsense.shared.entities.SharedFileResources
import org.pointyware.commonsense.shared.entities.SharedStringResources
import org.pointyware.commonsense.shared.ui.SharedDrawableResources
import org.pointyware.commonsense.shared.ui.SharedFontResources

/**
 *
 */
fun androidModule(application: Application) = module {
    single<SharedStringResources> { AndroidStringResources() }
    single<SharedFontResources> { AndroidFontResources() }
    single<SharedDrawableResources> { AndroidDrawableResources() }
    single<SharedFileResources> { AndroidFileResources() }

    single<Application> { application }
    single<Context> { application }
}
