package com.ro.githubprofile.data.remote

import com.ro.githubprofile.domain.model.Result
import com.ro.githubprofile.data.remote.api.GitHubApiService
import com.ro.githubprofile.data.remote.model.GHRepo
import javax.inject.Inject

/**
 * NetworkService is responsible for fetching repository data from the GitHub API.
 *
 * This class acts as a wrapper around the GitHubApiService and provides
 * a safe and clean way to perform network requests and handle their results.
 *
 * Key responsibilities:
 * - Format the query string to use a default (`language:swift`) if empty.
 * - Call the `searchRepositories` function of the injected GitHubApiService.
 * - Return a `Result.Success` if items are found.
 * - Return a `Result.Failure` with an exception if:
 *    - The list is empty.
 *    - An exception is thrown during the network call.
 *
 * This class is injected using Hilt and used in the repository layer.
 *
 * @param apiService The injected Retrofit service used to perform API calls.
 */
class NetworkService @Inject constructor(
    private val apiService: GitHubApiService
) {

    /**
     * Fetches GitHub repositories based on a search query.
     *
     * @param query The search term provided by the user.
     *              Defaults to "language:swift" if the query is blank.
     * @return A [Result] wrapping either the list of repositories on success
     *         or an exception on failure.
     */
    suspend fun fetchRepositories(query: String): Result<List<GHRepo>> {
        return try {
            val formattedQuery = if (query.isBlank()) "language:swift" else query
            val response = apiService.searchRepositories(formattedQuery)
            if (response.items.isNotEmpty()) {
                Result.Success(response.items)
            } else {
                Result.Failure(Exception("No repositories found"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}