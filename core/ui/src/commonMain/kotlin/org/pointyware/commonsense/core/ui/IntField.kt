/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.core.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value

/**
 * Presents a simple input field that allows a user to modify the value of a field with type [Type.Int]
 */
@Composable
fun IntField(
    value: Value.IntValue,
    modifier: Modifier = Modifier,
    onValueChange: (Value.IntValue)->Unit
) {
    TextField(
        value = value.toString(),
        onValueChange = {
            it.toIntOrNull()?.let { newValue ->
                onValueChange(Value.IntValue(newValue))
            }
        },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

/**
 * Presents a simple input field that allows a user to modify the value of a field with type [Int].
 */
@Composable
fun IntField(
    value: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit
) {
    TextField(
        value = value.toString(),
        onValueChange = {
            it.toIntOrNull()?.let { newValue ->
                onValueChange(newValue)
            }
        },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
