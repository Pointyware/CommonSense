/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.commonsense.core.entities.Field
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Type.Record
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.core.viewmodels.ViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Maintains the state of a [Record] being created and/or modified.
 */
interface RecordEditorViewModel {

    fun onFieldNameChange(newName: String)

    fun onFieldDefaultValueChange(newValue: Value<*>)

    fun onFieldTypeChange(newType: Type)
}

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
class RecordEditorViewModelImpl(

): ViewModel(), RecordEditorViewModel {

    // TODO: load from type repository ^^
    private val loadedTypes = listOf(
        Type.Int,
        Record("FooBar", uuid = Uuid.random()),
    )

    private fun newRecordState() = RecordEditorUiState(
        id = null,
        name = "untitled",
        fields = emptyList(),
        availableTypes = loadedTypes,
    )

    private val mutableState = MutableStateFlow(newRecordState())
    val state: StateFlow<RecordEditorUiState>
        get() = mutableState.asStateFlow()

    private val mutableOnFinish = MutableSharedFlow<Unit>()
    val onFinish: SharedFlow<Unit> get() = mutableOnFinish.asSharedFlow()

    fun prepareFor(record: Record?) {
        mutableState.value = record?.let {
            RecordEditorUiState(
                id = it.uuid,
                name = it.name,
                fields = it.fields.map {
                    FieldEditorUiState(
                        it.name,
                        it.type,
                        it.defaultValue
                    )
                },
                availableTypes = loadedTypes
            )
        } ?: newRecordState()
    }

    override fun onFieldNameChange(newName: String) {
        TODO("Not yet implemented")
    }

    override fun onFieldTypeChange(newType: Type) {
        TODO("Not yet implemented")
    }

    override fun onFieldDefaultValueChange(newValue: Value<*>) {
        TODO("Not yet implemented")
    }

    fun onRecordNameChange(newName: String) {
        mutableState.update {
            it.copy(name = newName)
        }
    }

    fun addField() {
        mutableState.update {
            val newField = Field("new field", Type.Int, Value.IntValue(0))
            it.copy(fields = it.fields + FieldEditorUiState(newField.name, newField.type, newField.defaultValue))
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
                val newFields = it.fields.mapIndexed { i, item ->
                    if (index == i) {
                        FieldEditorUiState(
                            item.name,
                            item.type,
                            value as Value<Type>
                        )
                    } else {
                        item
                    }
                }
                it.copy(
                    fields = newFields
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

    fun onConfirm() {
        viewModelScope.launch {
            // TODO: submit edits
            mutableOnFinish.emit(Unit)
        }
    }

    fun onCancel() {
        viewModelScope.launch {
            mutableOnFinish.emit(Unit)
        }
    }

    fun onFieldAdded() {
        TODO("Not yet implemented")
    }

    fun onFieldNameChange(index: Int, newName: String) {

    }

    fun onFieldTypeChange(index: Int, newType: Type) {

    }

    fun onFieldValueChange(index: Int, newValue: Value<*>) {

    }

    fun onFieldRemoved(index: Int) {

    }
}
