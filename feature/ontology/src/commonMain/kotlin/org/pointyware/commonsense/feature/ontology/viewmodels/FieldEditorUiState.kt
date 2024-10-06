/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value

/**
 *
 */
open class FieldEditorUiState<T: Type>(
    val name: String,
    val type: T,
    val value: Value<T>
)

/**
 *
 */
fun IntFieldEditorUiState(
    name: String,
    rawValue: Int
) = FieldEditorUiState(name = name, type = Type.Int, value = Value.IntValue(rawValue))
