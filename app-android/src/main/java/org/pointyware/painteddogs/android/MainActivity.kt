package org.pointyware.commonsense.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.remember
import org.pointyware.commonsense.shared.CommonSenseApp
import org.pointyware.commonsense.shared.di.getDependencies

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val appDependencies = remember { getDependencies() }
            CommonSenseApp(
                dependencies = appDependencies,
                isDarkTheme = isSystemInDarkTheme()
            )
        }
    }
}
