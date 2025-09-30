package com.ro.githubprofile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ro.githubprofile.data.remote.model.GHRepo
import com.ro.githubprofile.data.local.converters.OwnerConverter
import com.ro.githubprofile.data.local.dao.RepoDao

/**
 * Room database class for storing GitHub repositories locally.
 *
 * - Uses [GHRepo] as the only entity.
 * - Applies [com.ro.githubprofile.data.local.converters.OwnerConverter] for converting complex types (e.g., nested objects).
 * - Version is set to 4. Increment this number when the schema changes.
 * - `exportSchema = true` helps with keeping schema history (useful for migrations).
 */
@Database(
    entities = [GHRepo::class],
    version = 4,
    exportSchema = true
)
@TypeConverters(OwnerConverter::class)
abstract class GitHubDatabase : RoomDatabase() {

    /**
     * Abstract method to get the DAO (Data Access Object) for repositories.
     * - This allows Room to generate implementation at compile time.
     */
    abstract fun repoDao(): RepoDao

    companion object {
        /**
         * Constant for the database file name.
         */
        const val DATABASE_NAME = "github.db"
    }
}