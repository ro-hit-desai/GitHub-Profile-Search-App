package com.ro.githubprofile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ro.githubprofile.data.remote.model.GHRepo

/**
 * RepoDao is the Data Access Object (DAO) for the `GHRepo` entity.
 *
 * This interface defines methods for interacting with the local Room database,
 * including inserting, querying, and deleting repository data.
 *
 * All functions are `suspend` to support asynchronous execution with Kotlin coroutines.
 */
@Dao
interface RepoDao {

    /**
     * Inserts a list of repositories into the database.
     *
     * If a repository with the same primary key already exists,
     * it will be replaced with the new data.
     *
     * @param repositories The list of repositories to insert.
     */
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertRepositories(repositories: List<GHRepo>)

    /**
     * Searches repositories in the database based on the query string.
     *
     * The search is case-insensitive and matches against:
     * - Repository name
     * - Owner login
     * - Description
     *
     * @param query The search keyword entered by the user.
     * @return A list of repositories that match the search criteria.
     */
    @Query("""
        SELECT * FROM repositories 
        WHERE name LIKE '%' || :query || '%' 
        OR owner_login LIKE '%' || :query || '%'
        OR description LIKE '%' || :query || '%'
    """)
    suspend fun searchRepositories(query: String): List<GHRepo>

    /**
     * Retrieves all repositories stored in the database.
     *
     * @return A list of all saved repositories.
     */
    @Query("SELECT * FROM repositories")
    suspend fun getAllRepositories(): List<GHRepo>

    /**
     * Deletes all repositories from the database.
     *
     * Useful for clearing cached or outdated data.
     */
    @Query("DELETE FROM repositories")
    suspend fun clearRepositories()
}