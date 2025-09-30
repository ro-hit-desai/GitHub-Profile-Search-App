package com.ro.githubprofile.presentation.screens.about

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ro.githubprofile.presentation.theme.GitHubProfileTheme


/***
 * Displays the App Information screen with details about the app, technical highlights,
 * development tools, usage instructions, and version info.
 *
 * @param navController Navigation controller for handling navigation actions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "App Information",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // About Card
            InfoCard(
                title = "About the App",
                icon = Icons.Outlined.Lightbulb
            ) {
                Text(
                    text = "GitHub Explorer is a modern Android application built using Jetpack Compose. It helps users search and explore GitHub profiles with a smooth and intuitive UI.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                FeatureItem(
                    icon = Icons.Default.Search,
                    text = "Search GitHub profiles by username."
                )
                FeatureItem(
                    icon = Icons.Default.Storage,
                    text = "View profile details and repositories."
                )
                FeatureItem(
                    icon = Icons.Default.Palette,
                    text = "Dark and Light mode support."
                )
                FeatureItem(
                    icon = Icons.Default.Web,
                    text = "Integrated WebView for repository details."
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Technical Card
            InfoCard(
                title = "Technical Highlights",
                icon = Icons.Outlined.Code
            ) {
                Text(
                    text = "This project follows modern Android architecture guidelines:",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                BulletPoint("Jetpack Compose for declarative UI")
                BulletPoint("ViewModel and Kotlin Flows for state management")
                BulletPoint("Hilt for dependency injection")
                BulletPoint("Clean Architecture principles")
                BulletPoint("GitHub REST API integration")
                BulletPoint("Material 3 design system")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Development Tools Card
            InfoCard(
                title = "Development Tools",
                icon = Icons.Outlined.Computer
            ) {
                Text(
                    text = "Built with industry-standard Android development tools:",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                BulletPoint("Android Studio Giraffe | 2022.3.1")
                BulletPoint("Kotlin 1.9.0")
                BulletPoint("Android SDK 34")
                BulletPoint("Gradle 8.0")
                BulletPoint("Android Emulator with Pixel 6 profile")
                BulletPoint("Git for version control")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // How to Use Card
            InfoCard(
                title = "How to Use",
                icon = Icons.Outlined.Help
            ) {
                NumberedPoint(1, "Enter a GitHub username in the search field")
                NumberedPoint(2, "View the user's profile information")
                NumberedPoint(3, "Browse their repositories")
                NumberedPoint(4, "Toggle theme from settings")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Version Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Version 1.0.0",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    imageVector = Icons.Outlined.ListAlt,
                    contentDescription = "Version",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}


/**
 * Displays a styled card with a title, icon, and custom content.
 *
 * @param title The title text to display at the top of the card.
 * @param icon The icon to show next to the title.
 * @param content The composable content to render inside the card.
 */

@Composable
fun InfoCard(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}



/**
 * Displays a single feature item with an icon and description.
 * Used in the App Information card to list app features.
 */

@Composable
fun FeatureItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f))
    }
}



/**
 * Displays a single feature item with an bulllet points and description.
 * Used in the App Information card to list app features.
 */

@Composable
fun BulletPoint(text: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            "•",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


/**
 * Displays a single feature item with an number format and description.
 * Used in the App Information card to list app features.
 */


@Composable
fun NumberedPoint(number: Int, text: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            "$number.",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(end = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppInfoScreenPreview() {
    GitHubProfileTheme {
        AppInfoScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppInfoScreenDarkPreview() {
    GitHubProfileTheme(darkTheme = true) {
        AppInfoScreen(navController = rememberNavController())
    }
}