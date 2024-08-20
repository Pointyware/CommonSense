package di

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.test.check.checkKoinModules
import org.koin.test.verify.verify
import org.pointyware.commonsense.shared.di.appModule
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 */
class KoinValidationTest {

    lateinit var topLevelModule: Module

    @BeforeTest
    fun setUp() {
        topLevelModule = appModule()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun verifyKoinConfiguration() {
        topLevelModule.verify(extraTypes = listOf(HttpClientEngine::class, HttpClientConfig::class))
    }

    @Test
    fun checkKoinModules() {
        checkKoinModules(listOf(topLevelModule))
    }
}
