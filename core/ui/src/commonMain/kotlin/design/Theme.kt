package org.pointyware.commonsense.core.ui.design

/**
 * Combines primitives to create a Common Sense theme
 */

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import kotlinx.datetime.Instant
import org.jetbrains.compose.ui.tooling.preview.Preview

interface XPDateFormatter {
    fun format(date: Instant): String
}
val SimpleDateFormatter = object : XPDateFormatter {
    override fun format(date: Instant): String {
        return date.toString()
    }
}
val DateFormat = compositionLocalOf<XPDateFormatter> { throw IllegalStateException("DateFormat not provided") }

/**
 * Extends the Material3 Theme with an [XPDateFormatter].
 */
@Composable
fun CommonSenseTheme(
    isDark: Boolean = false,
    content: @Composable ()->Unit,
) {
    println("CommonSenseTheme")
    CompositionLocalProvider(
        DateFormat provides SimpleDateFormatter
    ) {
        MaterialTheme(
            colorScheme = if (isDark) darkColors else lightColors,
            shapes = shapes,
            typography = typography,
            content = content
        )
    }
}

@Preview
@Composable
fun CommonSenseThemePreview() {
    CommonSenseTheme {
        Surface {
            Text("ooh, a button")
            Button(onClick = { println("Click") }) {
                Text("Click me!")
            }
        }
    }
}
