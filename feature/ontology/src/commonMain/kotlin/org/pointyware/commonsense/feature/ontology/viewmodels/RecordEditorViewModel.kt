/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.pointyware.commonsense.core.entities.Field
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
        mutableState.update {
            it.fields.getOrNull(index)?.let { field ->
                val mutableFields = it.fields.toMutableList()
                mutableFields[index] = FieldEditorUiState(
                    newName,
                    field.type,
                    field.value
                )
                it.copy(
                    fields = mutableFields.toList()
                )
            } ?: it
        }
    }

    fun setFieldType(index: Int, type: Type) {
        mutableState.update {
            it.fields.getOrNull(index)?.let { field ->
                val mutableFields = it.fields.toMutableList()
                val newValue: Value<*> = when (type) {
                    is Type.Int -> {
                        Value.IntValue(0)
                    }
                    else -> {
                        Value.StringValue("Unsupported Type")
                    }
                }
                mutableFields[index] = FieldEditorUiState(
                    name = field.name,
                    type = type,
                    value = newValue as Value<Type>
                )
                it.copy(
                    fields = mutableFields.toList()
                )
            } ?: it
        }
    }

    fun setFieldValue(index: Int, value: Value<*>) {
        mutableState.update {
            it.fields.getOrNull(index)?.let { originalField ->
                val mutableFields = it.fields.toMutableList()
                mutableFields[index] = FieldEditorUiState(
                    originalField.name,
                    originalField.type,
                    value as Value<Type>
                )
                it.copy(
                    fields = mutableFields.toList()
                )
            } ?: it
        }
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
