package org.pointyware.commonsense.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.core.navigation.LocationRoot
import org.pointyware.commonsense.core.ui.design.CommonSenseTheme
import org.pointyware.commonsense.feature.ontology.navigation.ontologyRouting
import org.pointyware.commonsense.shared.di.AppDependencies

/**
 * The main entry point for the Common Sense app.
 */
@Composable
fun CommonSenseApp(
    dependencies: AppDependencies,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    Log.v("CommonSenseApp")
    val navController = remember { dependencies.getNavigationDependencies().getNavController() }

    CommonSenseTheme(
        isDark = isDarkTheme
    ) {
        CommonSenseScaffold(
            modifier = modifier,
            navController = navController,
        ) { paddingValues ->
            LocationRoot(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
            ) {
//                homeRouting()

                ontologyRouting()
                // Add more routing here
            }
        }
    }
}
