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
        mutableState.update {
            it.copy(name = newName)
        }
    }

    fun addField() {
        mutableState.update {
            val newField = Field("new field", Type.Int, Value.IntValue(0))
            it.copy(fields = it.fields + FieldEditorUiState(newField.name, newField.type, newField.value))
        }
    }

    fun setFieldName(index: Int, newName: String) {
        TODO("Not yet implemented")
    }

    fun setFieldType(index: Int, type: Type) {
        TODO("Not yet implemented")
    }

    fun setFieldValue(index: Int, value: Value<*>) {
        TODO("Not yet implemented")
    }

    fun removeField(index: Int) {
        mutableState.update {
            it.fields.getOrNull(index)?.let { removedField ->
                it.copy(
                    fields = it.fields - removedField
                )
            } ?: it
        }
    }
}
