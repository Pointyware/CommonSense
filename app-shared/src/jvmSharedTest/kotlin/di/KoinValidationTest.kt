package di

import org.koin.core.KoinApplication
import org.koin.test.check.checkModules
import org.pointyware.commonsense.feature.ontology.di.ontologyJvmSharedModule
import org.pointyware.commonsense.shared.di.setupKoin
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
