package com.ro.githubprofile.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ro.githubprofile.data.remote.model.GHRepo
import com.ro.githubprofile.presentation.theme.GitHubProfileTheme

/**
 * Displays a single GitHub repository item inside a Card layout.
 *
 * The card includes:
 * - Repository name (title)
 * - Optional description (if available)
 * - Owner's name
 * - Language used (if available)
 * - Star and fork count
 *
 * @param repo The GHRepo data model representing a GitHub repository.
 * @param onClick Callback when the card is tapped.
 * @param modifier Optional Modifier for external layout customization.
 */
@Composable
fun RepositoryItem(
    repo: GHRepo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Make the entire card clickable
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title: Repository name
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Optional description
            repo.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2, // Avoid overflowing too much
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Owner section: Icon + username
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Owner",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = repo.owner.login,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Footer: Language + Stars + Forks
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Language (if not null)
                repo.language?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Language",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Stars count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Stars",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = repo.stars.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Forks count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ForkRight,
                        contentDescription = "Forks",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = repo.forks.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Light theme preview of a sample repository item.
 */
@Preview(showBackground = true)
@Composable
fun RepositoryItemPreview() {
    GitHubProfileTheme {
        RepositoryItem(
            repo = GHRepo(
                id = 1,
                name = "Sample Repository",
                repoURL = "https://github.com/sample",
                owner = GHRepo.Owner("sampleuser"),
                ownerLogin = "sampleuser",
                description = "This is a sample repository description that might be a bit longer to test text wrapping",
                language = "Kotlin",
                stars = 42,
                forks = 10
            ),
            onClick = {}
        )
    }
}

/**
 * Dark theme preview of a different sample repository item.
 */
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RepositoryItemDarkPreview() {
    GitHubProfileTheme(darkTheme = true) {
        RepositoryItem(
            repo = GHRepo(
                id = 2,
                name = "Dark Mode Repo",
                repoURL = "https://github.com/dark",
                owner = GHRepo.Owner("darkuser"),
                ownerLogin = "darkuser",
                description = "Dark mode repository preview",
                language = "Java",
                stars = 100,
                forks = 25
            ),
            onClick = {}
        )
    }
}
