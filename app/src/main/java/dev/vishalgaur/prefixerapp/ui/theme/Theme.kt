package dev.vishalgaur.prefixerapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorScheme = lightColorScheme(
    primary = Color.Blue,
    secondary = Grey10,
    background = Grey10,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
     */
)

@Composable
fun PrefixerAppTheme(
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content,
    )
}
