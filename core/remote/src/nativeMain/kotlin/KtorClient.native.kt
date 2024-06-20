package org.pointyware.commonsense.core.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

/**
 *
 */
actual fun getClient(): HttpClient {
    return HttpClient(CIO) {

    }
}
