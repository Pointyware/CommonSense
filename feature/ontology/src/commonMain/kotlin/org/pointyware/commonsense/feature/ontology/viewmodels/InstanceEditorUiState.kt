/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.entities.Type

/**
 *
 */
data class InstanceEditorUiState(
    val type: Type.Record,
    val fields: List<FieldEditorUiState<Type>>,
)
