/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.core.viewmodels.ViewModel
import kotlin.uuid.ExperimentalUuidApi

interface InstanceEditorViewModel {
    val state: StateFlow<InstanceEditorUiState?>

    fun prepareFor(instance: Value.Instance?)
    fun onFieldValueChange(index: Int, newValue: Value<*>)
    fun onFinish()
}

/**
 *
 */
class InstanceEditorViewModelImpl(

): ViewModel(), InstanceEditorViewModel {

    @OptIn(ExperimentalUuidApi::class)
    private val mutableState = MutableStateFlow<InstanceEditorUiState?>(null)
    override val state: StateFlow<InstanceEditorUiState?>
        get() = mutableState.asStateFlow()

    override fun prepareFor(instance: Value.Instance?) {
        TODO("Not yet implemented")
    }

    override fun onFieldValueChange(index: Int, newValue: Value<*>) {
        TODO("Not yet implemented")
    }

    override fun onFinish() {
        TODO("Not yet implemented")
    }
}
