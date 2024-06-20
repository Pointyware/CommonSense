package org.pointyware.commonsense.android

import android.app.Application
import org.koin.core.context.startKoin
import org.pointyware.commonsense.android.di.androidModule
import org.pointyware.commonsense.shared.di.appModule

/**
 * This is the production Common Sense application; it performs production environment setup.
 */
class CSApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                androidModule(),
                appModule()
            )
        }
    }
}
