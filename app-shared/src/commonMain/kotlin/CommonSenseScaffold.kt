/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.shared

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.core.navigation.CommonSenseNavController

/**
 *
 */
@Composable
expect fun CommonSenseScaffold(
    modifier: Modifier = Modifier,
    navController: CommonSenseNavController,
    content: @Composable (PaddingValues) -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedScaffold(
    modifier: Modifier = Modifier,
    navController: CommonSenseNavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val currentLocation = navController.currentLocation.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    val stack = navController.backList.collectAsState()
                    if (stack.value.isNotEmpty()) {
                        IconButton(onClick = { navController.goBack() }) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Profile")
                        }
                    }
                },
                title = {
                    Text(currentLocation.value.toString() ?: "Common Sense")
                }
            )
        },
        content = content
    )
}
