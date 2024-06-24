package org.pointyware.commonsense.desktop

import androidx.compose.runtime.remember
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import org.pointyware.commonsense.desktop.di.desktopModule
import org.pointyware.commonsense.shared.CommonSenseApp
import org.pointyware.commonsense.shared.di.appModule
import org.pointyware.commonsense.shared.di.getDependencies

fun main() = application {

    startKoin {
        modules(
            desktopModule(),
            appModule()
        )
    }

    val appDependencies = remember { getDependencies() }

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
                Item(
                    text = "Save",
                    onClick = { /* Handle save action */ }
                )
                Item(
                    text = "Save As",
                    onClick = { /* Handle save as action */ }
                )
                Item(
                    text = "Load",
                    onClick = { /* Handle load action */ }
                )
            }
        }
    }


    Tray(
        icon = painterResource(Res.drawable.tray_icon),
        menu = {
            Menu("File") {
            }
        }
    )
}
