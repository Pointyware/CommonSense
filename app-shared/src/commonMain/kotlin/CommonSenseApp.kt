package org.pointyware.commonsense.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.core.navigation.LocationRoot
import org.pointyware.commonsense.core.ui.design.CommonSenseTheme
import org.pointyware.commonsense.shared.di.AppDependencies
import org.pointyware.commonsense.shared.home.homeRouting

/**
 * The main entry point for the Common Sense app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonSenseApp(
    dependencies: AppDependencies,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = dependencies.getNavigationDependencies().getNavController()

    CommonSenseTheme(
        isDark = isDarkTheme
    ) {
        val currentLocation = navController.currentLocation.collectAsState()
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
//                    colors = TopAppBarColors(
//                        containerColor =
//                        navigationIconContentColor =
//                        titleContentColor =
//                        actionIconContentColor =
//                        scrolledContainerColor =
//                    ),
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
                    },
                    actions = {
                    },
                )
            },
            floatingActionButton = {
            },
            floatingActionButtonPosition = FabPosition.End,
        ) { paddingValues ->
            LocationRoot(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
            ) {
                homeRouting()
                
                // Add more routing here
            }
        }
    }
}

fun logout() {
    println("Logging out")
    println("Logged out")
}
