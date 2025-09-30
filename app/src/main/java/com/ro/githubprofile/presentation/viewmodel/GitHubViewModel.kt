package com.ro.githubprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ro.githubprofile.data.remote.model.GHRepo
import com.ro.githubprofile.data.repository.GitHubRepository
import com.ro.githubprofile.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class GitHubViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

    private val _repos = MutableStateFlow<Result<List<GHRepo>>>(Result.Loading)
    val repos: StateFlow<Result<List<GHRepo>>> = _repos.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadInitialRepositories()
    }

    /**
     * Loads the initial list of GitHub repositories with an empty query.
     *
     * This function is called during ViewModel initialization to populate the UI
     * with a default list of repositories (could be trending or popular).
     *
     * It launches a coroutine in the ViewModel's scope and:
     * - Calls the repository's `searchRepositories` method with an empty string.
     * - Collects the emitted result (loading/success/error).
     * - Updates the `_repos` StateFlow with the result.
     *
     * This helps to show some default content before the user initiates a search.
     */
    private fun loadInitialRepositories() {
        viewModelScope.launch {
            repository.searchRepositories("").collect { result ->
                _repos.value = result
            }
        }
    }

    /**
     * Searches GitHub repositories based on the user-provided query.
     *
     * This function is typically triggered by user input (e.g., search bar).
     * It performs the following actions:
     * - Updates the `_searchQuery` StateFlow with the provided query.
     * - Launches a coroutine to collect the repository search result.
     * - Updates the `_repos` StateFlow with the collected result, which
     *   can be observed by the UI to display the corresponding data or error state.
     *
     * @param query The search term entered by the user to filter repositories.
     */
    fun searchRepositories(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            repository.searchRepositories(query).collect { result ->
                _repos.value = result
            }
        }
    }
}