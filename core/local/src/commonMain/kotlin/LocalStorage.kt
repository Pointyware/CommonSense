package org.pointyware.commonsense.core.local

/**
 * Multiplatform local data storage. Usually implemented as a file on most targets, but for web it could be localStorage.
 *
 * ```kotlin
 * class LocalStorage(path) {
 *
 * }
 *
 * expect object LocalStorageConstants {
 *   val maxBytes: Long
 * }
 * ```
 */
interface LocalStorage {
    val path: String
}

//expect fun file(path: String): KmpFile
