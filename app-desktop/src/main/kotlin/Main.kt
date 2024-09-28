package org.pointyware.commonsense.desktop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import org.pointyware.commonsense.desktop.di.desktopModule
import org.pointyware.commonsense.shared.CommonSenseApp
import org.pointyware.commonsense.shared.FileViewModel
import org.pointyware.commonsense.shared.di.appModule
import org.pointyware.commonsense.shared.di.getDependencies
import org.pointyware.commonsense.shared.di.setupKoin

fun main() = application {

    setupKoin(platformModule = desktopModule())

    val appDependencies = remember { getDependencies() }
    val drawableResources = remember { appDependencies.getDrawableResources() }

    val state = rememberWindowState()
    Window(
        title = "My Application",
        state = state,
        onCloseRequest = this::exitApplication
    ) {
        CommonSenseApp(
            dependencies = appDependencies,
            isDarkTheme = false
        )

        MenuBar {
            Menu("File") {
                fileMenuItems(appDependencies.getFileViewModel())
            }
        }
    }


    Tray(
        icon = painterResource(drawableResources.appIconSmall),
        menu = {
            Menu("File") {
                fileMenuItems(appDependencies.getFileViewModel())
            }
        }
    )
}

@Composable
fun MenuScope.fileMenuItems(fileViewModel: FileViewModel) {
    Item(
        text = "Save",
        onClick = fileViewModel::save
    )
    Item(
        text = "Save As",
        onClick = fileViewModel::saveAs
    )
    Item(
        text = "Load",
        onClick = fileViewModel::load
    )
}
