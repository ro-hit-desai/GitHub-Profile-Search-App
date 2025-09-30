package com.ro.githubprofile.data.repository

import com.ro.githubprofile.domain.model.Result
import com.ro.githubprofile.data.local.dao.RepoDao
import com.ro.githubprofile.data.remote.NetworkService
import com.ro.githubprofile.data.remote.model.GHRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository class that acts as a single source of truth for GitHub data.
 *
 * - It fetches data from the network and caches it into the local Room database.
 * - Provides a flow-based API to observe data changes.
 *
 * @property networkService Handles API requests to GitHub.
 * @property repoDao Handles database operations for repository data.
 */
class GitHubRepository @Inject constructor(
    private val networkService: NetworkService,
    private val repoDao: RepoDao
) {
    /**
     * Searches GitHub repositories based on the provided query string.
     *
     * The function:
     * - Emits a [Result.Loading] state at the start.
     * - Tries to fetch repositories from the network.
     * - On success:
     *   - Maps each repository to include the owner's login as a separate field.
     *   - Clears old data and inserts new data into the local database.
     *   - Emits [Result.Success] with the updated list.
     * - On failure:
     *   - Tries to load cached results from the local database using a LIKE query.
     *   - Emits [Result.Success] if cached data exists, otherwise emits [Result.Failure].
     *
     * @param query The search term entered by the user.
     * @return A Flow emitting [Result] states: Loading, Success, or Failure.
     */
    fun searchRepositories(query: String): Flow<Result<List<GHRepo>>> = flow {
        emit(Result.Loading)

        try {
            val result = networkService.fetchRepositories(query)
            when (result) {
                is Result.Success -> {
                    val reposWithOwnerLogin = result.data.map { repo ->
                        repo.copy(ownerLogin = repo.owner.login)
                    }
                    repoDao.clearRepositories()
                    repoDao.insertRepositories(reposWithOwnerLogin)
                    emit(Result.Success(reposWithOwnerLogin))
                }

                is Result.Failure -> {
                    val cached = repoDao.searchRepositories("%$query%")
                    if (cached.isNotEmpty()) {
                        emit(Result.Success(cached))
                    } else {
                        emit(result)
                    }
                }

                else -> {}
            }
        } catch (e: Exception) {
            val cached = repoDao.searchRepositories("%$query%")
            if (cached.isNotEmpty()) {
                emit(Result.Success(cached))
            } else {
                emit(Result.Failure(e))
            }
        }
    }
}