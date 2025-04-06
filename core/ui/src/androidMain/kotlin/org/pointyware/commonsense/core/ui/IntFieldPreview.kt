/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.core.ui.org.pointyware.commonsense.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.pointyware.commonsense.core.ui.IntField
import org.pointyware.commonsense.core.ui.PopularSizePreviews

@PopularSizePreviews
@Composable
fun IntFieldPreview(

) {
    var value by remember { mutableIntStateOf(10) }
    IntField(
        value = value,
        onValueChange = { value = it },
    )
}
