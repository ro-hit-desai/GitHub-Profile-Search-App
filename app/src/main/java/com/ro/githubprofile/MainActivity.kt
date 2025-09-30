package com.ro.githubprofile

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.ro.githubprofile.presentation.screens.about.AppContent
import com.ro.githubprofile.presentation.theme.GitHubProfileTheme
import com.ro.githubprofile.util.LocalPreferencesManager
import com.ro.githubprofile.util.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MainActivity serves as the entry point of the application.
 *
 * Key responsibilities:
 * - Injects PreferencesManager using Hilt for accessing stored user preferences.
 * - Enables WebView debugging in debug builds.
 * - Sets the Composable content with proper theme and preference support.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // PreferencesManager is injected using Hilt to persist and retrieve user settings
    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable WebView debugging if the build variant ends with ".debug"
        if (applicationContext.packageName.endsWith(".debug")) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        // Set the main Composable content and pass PreferencesManager via CompositionLocalProvider
        setContent {
            CompositionLocalProvider(LocalPreferencesManager provides preferencesManager) {
                GitHubProfileAppContent()
            }
        }
    }
}

/**
 * Root composable for the app's content.
 *
 * Responsibilities:
 * - Observes darkTheme preference from PreferencesManager.
 * - Provides GitHubProfileTheme according to the current preference.
 * - Handles theme change requests and persists them.
 *
 * @param onBackPressed Currently unused but can be extended for back navigation handling.
 */
@Composable
private fun GitHubProfileAppContent() {
    val preferencesManager = LocalPreferencesManager.current
    val darkTheme by preferencesManager.darkThemeFlow.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    GitHubProfileTheme(darkTheme = darkTheme) {
        AppContent(
            onThemeUpdated = { newDarkTheme ->
                coroutineScope.launch {
                    preferencesManager.setDarkTheme(newDarkTheme)
                }
            },
            darkTheme = darkTheme,
            onBackPressed = {}
        )
    }
}
