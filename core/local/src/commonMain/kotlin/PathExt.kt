package org.pointyware.commonsense.core.local

import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readString

/**
 * Reads the text content of the file at this path in UTF-8 encoding.
 */
fun Path.readText(): String {
    return Buffer().use {
        SystemFileSystem.source(this).use { source ->
            source.readAtMostTo(it, Long.MAX_VALUE)
        }
        it.readString()
    }
}

/**
 * Writes the specified [text] to this file in UTF-8 encoding, creating the file if it does not exist.
 */
fun Path.writeText(text: String) {
    Buffer().use {
        it.write(text.encodeToByteArray())
        SystemFileSystem.sink(this).use { sink ->
            sink.write(it, it.size)
        }
    }
}
