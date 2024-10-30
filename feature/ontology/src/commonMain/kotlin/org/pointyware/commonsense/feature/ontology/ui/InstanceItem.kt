/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.feature.ontology.viewmodels.InstanceItemUiState

/**
 *
 */
@Composable
fun InstanceItem(
    state: InstanceItemUiState,
    modifier: Modifier = Modifier,
    showCheckbox: Boolean,
    onCheckChange: (Boolean)->Unit,
) {
    Row(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = showCheckbox,
        ) {
            Checkbox(
                checked = false, // TODO: add selection state
                onCheckedChange = onCheckChange
            )
        }
        Text(
            text = state.description,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
