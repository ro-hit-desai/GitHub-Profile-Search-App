package com.ro.githubprofile.data.remote.api

import com.ro.githubprofile.data.remote.model.GitHubApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service interface for accessing GitHub's repository search API.
 *
 * Defines a single suspend function for searching repositories using
 * the endpoint: https://api.github.com/search/repositories
 */
interface GitHubApiService {

    /**
     * Performs a GET request to search GitHub repositories.
     *
     * @param query The search term used to find repositories (e.g., "android", "machine learning").
     * @param sort Optional. Sort field. One of `stars`, `forks`, or `updated`. Default is `stars`.
     * @param order Optional. Sorting order: `desc` (descending) or `asc` (ascending). Default is `desc`.
     * @param perPage Optional. Number of results per page (max 100). Default is 100.
     *
     * @return A [GitHubApiResponse] object containing the total count,
     *         incomplete results flag, and a list of [GHRepo] items.
     */
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int = 100
    ): GitHubApiResponse
}