/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.core.ui

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 *
 */
@Composable
fun BooleanField(
    value: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (Boolean)->Unit
) {
    Checkbox(
        checked = value,
        modifier = modifier,
        onCheckedChange = { newValue: Boolean ->
            onValueChange(newValue)
        },
    )
}
