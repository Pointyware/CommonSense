/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.shared

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.core.navigation.CommonSenseNavController

/**
 *
 */
@Composable
actual fun CommonSenseScaffold(
    modifier: Modifier,
    navController: CommonSenseNavController,
    content: @Composable (PaddingValues) -> Unit
) {
    // TODO: implement desktop-specific UI
}
