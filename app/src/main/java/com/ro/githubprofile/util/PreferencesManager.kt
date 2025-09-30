package com.ro.githubprofile.util

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Extension property to create a single instance of DataStore named "settings"
private val Context.dataStore by preferencesDataStore(name = "settings")

/**
 * CompositionLocal for accessing PreferencesManager within a Composable hierarchy.
 *
 * Usage:
 * CompositionLocalProvider(LocalPreferencesManager provides preferencesManager) { ... }
 */
val LocalPreferencesManager = compositionLocalOf<PreferencesManager> {
    error("No PreferencesManager provided")
}

/**
 * PreferencesManager is a singleton class responsible for managing
 * user preferences using Jetpack DataStore.
 *
 * Key Responsibilities:
 * - Read and write user preferences (currently dark theme).
 * - Provide a Flow<Boolean> to observe the dark theme setting in real-time.
 * - Expose a suspend function to persist changes to the dark theme preference.
 *
 * Annotated with @Singleton for dependency injection with Hilt,
 * ensuring a single instance across the app.
 *
 * @param context Application context injected by Hilt.
 */
@Singleton
class PreferencesManager @Inject constructor(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        // Preference key used to store the dark theme setting
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    }

    /**
     * A Flow that emits the current dark theme setting.
     * Default value is `false` (light theme) if not set.
     */
    open val darkThemeFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }

    /**
     * Updates the dark theme preference in DataStore.
     *
     * @param enabled `true` to enable dark theme, `false` to disable it.
     */
    open suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = enabled
        }
    }
}
