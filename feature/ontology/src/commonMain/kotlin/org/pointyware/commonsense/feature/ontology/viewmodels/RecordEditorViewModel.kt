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
    val state: StateFlow<RecordEditorUiState>
    val onFinish: SharedFlow<Unit>
    fun prepareFor(record: Record?)
    fun onRecordNameChange(newName: String)
    fun onFieldAdded()
    fun onFieldNameChange(index: Int, newName: String)
    fun onFieldValueChange(index: Int, newValue: Value<*>)
    fun onFieldTypeChange(index: Int, newType: Type)
    fun onFieldRemoved(index: Int)
    fun onConfirm()
    fun onCancel()
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
    override val state: StateFlow<RecordEditorUiState>
        get() = mutableState.asStateFlow()

    private val mutableOnFinish = MutableSharedFlow<Unit>()
    override val onFinish: SharedFlow<Unit> get() = mutableOnFinish.asSharedFlow()

    override fun prepareFor(record: Record?) {
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

    override fun onRecordNameChange(newName: String) {
        mutableState.update {
            it.copy(name = newName)
        }
    }

    override fun onFieldAdded() {
        mutableState.update {
            val newField = Field("new field", Type.Int, Value.IntValue(0))
            it.copy(fields = it.fields + FieldEditorUiState(newField.name, newField.type, newField.defaultValue))
        }
    }

    override fun onFieldNameChange(index: Int, newName: String) {
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

    override fun onFieldTypeChange(index: Int, newType: Type) {
        mutableState.update {
            it.fields.getOrNull(index)?.let { field ->
                val mutableFields = it.fields.toMutableList()
                val newValue: Value<*> = when (newType) {
                    is Type.Int -> {
                        Value.IntValue(0)
                    }
                    else -> {
                        Value.StringValue("Unsupported Type")
                    }
                }
                mutableFields[index] = FieldEditorUiState(
                    name = field.name,
                    type = newType,
                    value = newValue as Value<Type>
                )
                it.copy(
                    fields = mutableFields.toList()
                )
            } ?: it
        }
    }

    override fun onFieldValueChange(index: Int, newValue: Value<*>) {
        mutableState.update {
            it.fields.getOrNull(index)?.let { originalField ->
                val newFields = it.fields.mapIndexed { i, item ->
                    if (index == i) {
                        FieldEditorUiState(
                            item.name,
                            item.type,
                            newValue as Value<Type>
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

    override fun onFieldRemoved(index: Int) {
        mutableState.update {
            it.fields.getOrNull(index)?.let { removedField ->
                it.copy(
                    fields = it.fields - removedField
                )
            } ?: it
        }
    }

    override fun onConfirm() {
        viewModelScope.launch {
            // TODO: submit edits
            mutableOnFinish.emit(Unit)
        }
    }

    override fun onCancel() {
        viewModelScope.launch {
            mutableOnFinish.emit(Unit)
        }
    }
}
