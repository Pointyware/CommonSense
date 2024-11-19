/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.shared.di

import org.koin.core.KoinApplication
import org.koin.test.check.checkModules
import org.pointyware.commonsense.feature.ontology.di.ontologyJvmSharedModule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
class KoinValidationTest {

    lateinit var koinApp: KoinApplication

    @BeforeTest
    fun setUp() {
        koinApp = setupKoin(platformModule = ontologyJvmSharedModule())
    }

    @AfterTest
    fun stopKoin() {
        koinApp.close()
    }

    @Test
    fun checkKoinModules() {
        koinApp.checkModules()
    }
}
