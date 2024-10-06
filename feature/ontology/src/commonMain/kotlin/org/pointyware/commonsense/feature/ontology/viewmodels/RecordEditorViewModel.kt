/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.pointyware.commonsense.core.entities.Record
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.core.viewmodels.ViewModel

/**
 *
 */
class RecordEditorViewModel(

): ViewModel() {

    // TODO: load from type repository ^^
    private val loadedTypes = listOf(
        Type.Int,
        Record("FooBar"),
    )

    private val mutableState = MutableStateFlow(RecordEditorUiState(
        name = "untitled",
        fields = emptyList(),
        availableTypes = loadedTypes,
    ))
    val state: StateFlow<RecordEditorUiState>
        get() = mutableState.asStateFlow()

    fun onRecordNameChange(newName: String) {
        TODO("Not yet implemented")
    }

    fun addField() {
        TODO("delegate to RecordEditorViewModel")
    }

    fun setFieldType(index: Int, type: Type) {
        TODO("Not yet implemented")
    }

    fun setFieldValue(index: Int, value: Value<*>) {
        TODO("Not yet implemented")
    }

    fun removeField(index: Int) {
        TODO("Not yet implemented")
    }
}
