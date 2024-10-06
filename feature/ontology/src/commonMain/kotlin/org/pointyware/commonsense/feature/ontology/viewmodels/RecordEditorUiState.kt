/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.viewmodels

/**
 *
 */
data class RecordEditorUiState(
    val name: String,
    val fields: List<FieldEditorUiState<*>>
)
