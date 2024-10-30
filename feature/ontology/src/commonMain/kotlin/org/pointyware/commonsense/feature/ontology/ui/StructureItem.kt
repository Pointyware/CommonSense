/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.feature.ontology.viewmodels.RecordItemUiState

/**
 * Displays a single [org.pointyware.commonsense.core.entities.Type.Record] as a list item.
 */
@Composable
fun StructureItem(
    state: RecordItemUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = state.name
        )
    }
}
