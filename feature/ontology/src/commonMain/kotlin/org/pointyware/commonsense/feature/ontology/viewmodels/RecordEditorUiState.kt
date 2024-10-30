/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.entities.Type
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents the state of a [Record] being created and/or modified.
 */
@OptIn(ExperimentalUuidApi::class)
data class RecordEditorUiState(
    val id: Uuid?,
    val name: String,
    val fields: List<FieldEditorUiState<Type>>,
    val availableTypes: List<Type>
)
