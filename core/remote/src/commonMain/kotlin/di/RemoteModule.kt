package org.pointyware.commonsense.core.remote.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.pointyware.commonsense.core.remote.getClient

/**
 *
 */
fun coreRemoteModule() = module {
    single<HttpClient> { getClient() }
//    single { RemoteInteractor(get()) }
//    single { RemoteViewModel(get()) }
}
