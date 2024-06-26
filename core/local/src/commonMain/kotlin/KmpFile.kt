package org.pointyware.commonsense.core.local

/**
 * A Kotlin-Multiplatform file.
 *
 * TODO: rename/rework as LocalStorage
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
interface KmpFile {
    val path: String
}

//expect fun file(path: String): KmpFile
