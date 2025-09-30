package com.ro.githubprofile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ro.githubprofile.presentation.screens.about.AppInfoScreen
import com.ro.githubprofile.presentation.screens.detail.RepositoryDetailScreen
import com.ro.githubprofile.presentation.screens.home.RepositoryListScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Sets up the navigation graph for the app using Jetpack Navigation Compose.
 *
 * @param navController Controller used to manage app navigation.
 * @param darkTheme Indicates whether the dark theme is currently active.
 * @param modifier Optional [Modifier] for styling the NavHost.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    darkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "repository_list",
        modifier = modifier
    ) {

        /**
         * Composable destination for the Repository List screen.
         * - Triggered on app start (startDestination).
         * - Accepts a click callback with the repository URL.
         * - URL is encoded to safely pass it through the navigation route.
         */
        composable("repository_list") {
            RepositoryListScreen(
                onRepoClick = { url ->
                    val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                    navController.navigate("repository_detail/$encodedUrl")
                }
            )
        }

        /**
         * Composable destination for the Repository Detail screen.
         * - Accepts a single argument `url` from the route.
         * - Decodes the URL received from the previous screen.
         * - Displays detailed information about the repository.
         * - Provides a back button that navigates up in the stack.
         */
        composable(
            route = "repository_detail/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url") ?: ""
            val url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())

            RepositoryDetailScreen(
                url = url,
                darkTheme = darkTheme,
                onBack = { navController.popBackStack() }
            )
        }

        /**
         * Composable destination for the App Info (About) screen.
         * - Displays information about the app.
         * - Includes a back navigation option via [navController].
         */
        composable("about") {
            AppInfoScreen(navController = navController)
        }
    }
}
