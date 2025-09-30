package com.ro.githubprofile.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ro.githubprofile.presentation.viewmodel.GitHubViewModel
import com.ro.githubprofile.domain.model.Result
import com.ro.githubprofile.presentation.components.RepositoryItem

/**
 * Main screen to display a list of GitHub repositories based on a search query.
 *
 * @param onRepoClick Called when a repository is clicked (usually opens the repo URL).
 * @param viewModel Injected ViewModel (uses Hilt by default).
 */
@Composable
fun RepositoryListScreen(
    onRepoClick: (String) -> Unit,
    viewModel: GitHubViewModel = hiltViewModel()
) {
    // Observes repository search results and current query state from ViewModel
    val repos by viewModel.repos.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Search bar at the top
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.searchRepositories(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            // UI state handler: loading, success, failure
            when (repos) {
                is Result.Loading -> {
                    // Loading indicator
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Result.Success -> {
                    val repositories = (repos as Result.Success).data

                    // No results state
                    if (repositories.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No repositories found. Try a different search.")
                        }
                    } else {
                        // Display list of repositories using LazyColumn
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(repositories) { repo ->
                                RepositoryItem(
                                    repo = repo,
                                    onClick = { onRepoClick(repo.repoURL) }
                                )
                            }
                        }
                    }
                }

                is Result.Failure -> {
                    // Error state with retry option
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Error: ${(repos as Result.Failure).exception.message}")
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = { viewModel.searchRepositories(searchQuery) }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * A reusable search bar UI for text-based queries.
 *
 * @param query Current input in the search field.
 * @param onQueryChange Callback triggered when input changes.
 * @param modifier Modifier to customize layout externally.
 * @param placeholder Hint text shown when query is empty.
 */
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search repositories..."
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(36.dp)), // Rounded input shape
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp), // Inner shape for input field
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
    )
}
