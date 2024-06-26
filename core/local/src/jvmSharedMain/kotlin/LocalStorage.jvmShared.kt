package org.pointyware.commonsense.core.local

class JvmFile(
    override val path: String
): LocalStorage {

}

actual fun file(path: String): LocalStorage {
    return JvmFile(path)
}
