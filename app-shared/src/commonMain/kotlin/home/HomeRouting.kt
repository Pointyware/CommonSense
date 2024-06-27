package org.pointyware.commonsense.shared.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.navigation.LocationRootScope
import org.pointyware.commonsense.core.navigation.StaticRoute
import org.pointyware.commonsense.shared.home.di.HomeDependencies

val homeRoute = StaticRoute("", Unit)
/**
 * Sets up all routes for home navigation.
 */
@Composable
fun LocationRootScope<Any, Any>.homeRouting() {
    val di = remember { getKoin() }
    val profileDependencies = remember { di.get<HomeDependencies>() }

    location(homeRoute) {
        val homeViewModel = remember { profileDependencies.getHomeViewModel() }
        val mapper = remember { profileDependencies.getHomeUiStateMapper() }
        val state = homeViewModel.state.collectAsState()
        HomeScreen(
            state = mapper.map(state.value),
        )
    }
}
