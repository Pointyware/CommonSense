package org.pointyware.commonsense.desktop.di

import org.jetbrains.compose.resources.DrawableResource
import org.pointyware.commonsense.desktop.Res
import org.pointyware.commonsense.desktop.tray_icon
import org.pointyware.commonsense.shared.ui.SharedDrawableResources

class DesktopDrawableResources : SharedDrawableResources {
    override val appIconSmall: DrawableResource
        get() = Res.drawable.tray_icon
}
