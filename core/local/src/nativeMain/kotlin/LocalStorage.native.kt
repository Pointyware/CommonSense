package org.pointyware.commonsense.core.local


class NativeFile(
    override val path: String
): LocalStorage {

}

actual fun file(path: String): LocalStorage {
    return NativeFile(path)
}
