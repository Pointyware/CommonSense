package org.pointyware.commonsense.core.remote

import io.ktor.client.HttpClient

/**
 * Creates a ktor client for any platform.
 */
expect fun getClient(): HttpClient
