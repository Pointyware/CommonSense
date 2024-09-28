package org.pointyware.commonsense.android

import android.app.Application
import org.koin.dsl.module
import org.pointyware.commonsense.android.di.androidModule
import org.pointyware.commonsense.shared.di.setupKoin

/**
 * This is the production Common Sense application; it performs production environment setup.
 */
class CSApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        setupKoin(
            platformModule = module {
                includes(androidModule(this@CSApplication))
            }
        )
    }
}
