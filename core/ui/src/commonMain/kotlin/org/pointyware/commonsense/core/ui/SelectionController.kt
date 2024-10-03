package org.pointyware.commonsense.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


interface SelectionController<T: Any> {
    var isSelectionActive: Boolean
    fun select(key: T)
    fun deselect(key: T)
    fun isSelected(key: T): Boolean
    fun activate()
    fun deactivate()
}

internal class SelectionControllerMapImpl<T: Any>(): SelectionController<T> {

    override var isSelectionActive: Boolean = false

    private val selectedKeys = mutableSetOf<T>()

    override fun select(key: T) {
        selectedKeys.add(key)
    }

    override fun deselect(key: T) {
        selectedKeys.remove(key)
    }

    override fun isSelected(key: T): Boolean {
        return selectedKeys.contains(key)
    }

    override fun activate() {
        isSelectionActive = true
    }

    override fun deactivate() {
        isSelectionActive = false
        selectedKeys.clear()
    }
}

/**
 * Creates a new SelectionController.
 */
@Composable
fun <T : Any> rememberSelectionController(): SelectionController<T> = remember {
    SelectionControllerMapImpl()
}
