package com.ro.githubprofile.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Custom dark color scheme for the app.
 * Defines the colors used in dark theme mode.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF3700B3),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

/**
 * Custom light color scheme for the app.
 * Defines the colors used in light theme mode.
 */
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF3700B3),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

/**
 * Applies the GitHubProfileTheme to the app's UI using Material 3 theming.
 *
 * @param darkTheme A boolean flag that determines whether to apply the dark theme.
 *                  Defaults to the system setting using [isSystemInDarkTheme()].
 * @param content The UI content that will be wrapped in this theme.
 *
 * This function selects the appropriate color scheme (light or dark) and applies it
 * along with the custom [Typography] (defined elsewhere) using [MaterialTheme].
 */
@Composable
fun GitHubProfileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Assume Typography is defined elsewhere
        content = content
    )
}
