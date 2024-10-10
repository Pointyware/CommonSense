/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.core.ui

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value

/**
 * Presents a simple input field that allows a user to modify the value of a field with
 * type [Type.String]
 */
@Composable
fun StringField(
    value: Value.StringValue,
    modifier: Modifier = Modifier,
    onValueChange: (Value.StringValue)->Unit
) {
    TextField(
        value = value.toString(),
        onValueChange = {
            onValueChange(Value.StringValue(it))
        },
        modifier = modifier
    )
}
