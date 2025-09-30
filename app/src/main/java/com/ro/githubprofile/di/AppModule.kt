package com.ro.githubprofile.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ro.githubprofile.data.remote.api.GitHubApiService
import com.ro.githubprofile.data.local.GitHubDatabase
import com.ro.githubprofile.data.repository.GitHubRepository
import com.ro.githubprofile.data.remote.NetworkService
import com.ro.githubprofile.util.PreferencesManager
import com.ro.githubprofile.data.local.dao.RepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Migration from version 3 to 4 of the Room database.
     * - Creates a new table `repositories_new` with updated schema.
     * - Copies data from the old `repositories` table to the new one, mapping old column names to new ones.
     * - Drops the old table and renames the new table to `repositories`.
     */
    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
                CREATE TABLE IF NOT EXISTS repositories_new (
                    id INTEGER PRIMARY KEY NOT NULL,
                    name TEXT NOT NULL,
                    repo_url TEXT NOT NULL,
                    owner_data TEXT NOT NULL,
                    owner_login TEXT NOT NULL,
                    description TEXT,
                    language TEXT,
                    stars INTEGER NOT NULL,
                    forks INTEGER NOT NULL
                )
            """)

            database.execSQL("""
                INSERT INTO repositories_new (id, name, repo_url, owner_data, owner_login, description, language, stars, forks)
                SELECT id, name, repoURL, owner, owner_login, description, language, stargazers_count, forks_count 
                FROM repositories
            """)

            database.execSQL("DROP TABLE repositories")
            database.execSQL("ALTER TABLE repositories_new RENAME TO repositories")
        }
    }

    /**
     * Provides a singleton instance of [GitHubApiService] using Retrofit.
     * This service will be used to make API calls to GitHub.
     */
    @Provides
    @Singleton
    fun provideGitHubApiService(): GitHubApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApiService::class.java)
    }

    /**
     * Provides a singleton instance of [NetworkService].
     * It wraps the GitHub API service and manages network operations.
     */
    @Provides
    @Singleton
    fun provideNetworkService(apiService: GitHubApiService): NetworkService {
        return NetworkService(apiService)
    }

    /**
     * Provides a singleton instance of [GitHubDatabase] using Room.
     * Adds migration strategy and fallback policy.
     */
    @Provides
    @Singleton
    fun provideDatabase(app: Application): GitHubDatabase {
        return Room.databaseBuilder(
            app,
            GitHubDatabase::class.java,
            GitHubDatabase.Companion.DATABASE_NAME
        )
            .addMigrations(MIGRATION_3_4)
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    /**
     * Provides a singleton instance of [RepoDao].
     * This DAO interface handles database operations related to repositories.
     */
    @Provides
    @Singleton
    fun provideRepoDao(database: GitHubDatabase): RepoDao {
        return database.repoDao()
    }

    /**
     * Provides a singleton instance of [GitHubRepository].
     * It acts as the single source of truth by combining local and network data sources.
     */
    @Provides
    @Singleton
    fun provideGitHubRepository(
        networkService: NetworkService,
        repoDao: RepoDao
    ): GitHubRepository {
        return GitHubRepository(networkService, repoDao)
    }

    /**
     * Provides a singleton instance of [PreferencesManager].
     * Used to store and retrieve shared preferences throughout the app.
     */
    @Provides
    @Singleton
    fun providePreferencesManager(app: Application): PreferencesManager {
        return PreferencesManager(app)
    }
}