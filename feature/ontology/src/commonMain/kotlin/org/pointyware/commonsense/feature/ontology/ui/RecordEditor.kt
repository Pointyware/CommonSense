/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.viewmodels.RecordEditorUiState

/**
 * Allows a user to create a new [Record] or edit an existing [Record].
 */
@Composable
fun RecordEditor(
    state: RecordEditorUiState,
    modifier: Modifier = Modifier,
    onNameChange: (String)->Unit,
    onFieldAdded: ()->Unit,
    onFieldTypeChanged: (index: Int, newType: Type)->Unit,
    onFieldValueChanged: (index: Int, newValue: Value<*>)->Unit,
    onFieldRemoved: (index: Int)->Unit,
) {


}
