/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


interface SelectionController<T: Any> {
    val selectedItems: MutableState<Set<T>>
    val isSelectionActive: MutableState<Boolean>
    fun select(key: T)
    fun deselect(key: T)
    fun isSelected(key: T): Boolean
    fun activate()
    fun deactivate()
}

internal class SelectionControllerMapImpl<T: Any> : SelectionController<T> {

    override val selectedItems: MutableState<Set<T>> = mutableStateOf(emptySet())
    override val isSelectionActive: MutableState<Boolean> = mutableStateOf(false)

    override fun select(key: T) {
        selectedItems.value += key
    }

    override fun deselect(key: T) {
        selectedItems.value -= key
    }

    override fun isSelected(key: T): Boolean {
        return selectedItems.value.contains(key)
    }

    override fun activate() {
        isSelectionActive.value = true
    }

    override fun deactivate() {
        isSelectionActive.value = false
        selectedItems.value = emptySet()
    }
}

/**
 * Creates a new SelectionController.
 */
@Composable
fun <T : Any> rememberSelectionController(): SelectionController<T> = remember {
    SelectionControllerMapImpl()
}
