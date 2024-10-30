/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.entities.Type

/**
 * Represents the state of a [Record] being created and/or modified.
 */
data class RecordEditorUiState(
    val name: String,
    val fields: List<FieldEditorUiState<Type>>,
    val availableTypes: List<Type>
)
