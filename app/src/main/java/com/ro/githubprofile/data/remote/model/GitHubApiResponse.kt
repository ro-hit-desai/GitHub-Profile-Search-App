package com.ro.githubprofile.data.remote.model

/**
 * Data class representing the response from the GitHub Search Repositories API.
 *
 * This class maps the JSON structure returned from:
 * https://api.github.com/search/repositories?q=...
 *
 * @property total_count The total number of repositories matching the search query.
 * @property incomplete_results Boolean indicating whether the results are incomplete
 * due to rate limiting or partial data from GitHub.
 * @property items A list of [GHRepo] items representing the matched repositories.
 */
data class GitHubApiResponse(

    /**
     * Total number of repositories found for the search query.
     * This is useful for pagination and displaying overall search results.
     */
    val total_count: Int,

    /**
     * Indicates if the returned results are incomplete.
     * GitHub may set this to true if the search results exceed their limits.
     */
    val incomplete_results: Boolean,

    /**
     * List of repositories that matched the search query.
     * Each item is represented by the [GHRepo] data class.
     */
    val items: List<GHRepo>
)
