package com.ro.githubprofile.data.remote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Represents a GitHub repository entity for Room database and Retrofit API parsing.
 * - Annotated with @Entity to define the SQLite table name as "repositories".
 * - Each property is mapped from the API using @SerializedName and stored using @ColumnInfo.
 */
@Entity(tableName = "repositories")
data class GHRepo(

    /**
     * Unique ID of the repository.
     * - Serves as the primary key in the Room database.
     */
    @PrimaryKey
    val id: Int,

    /**
     * Name of the repository.
     */
    val name: String,

    /**
     * URL to the GitHub repository (mapped from "html_url").
     * - Stored as "repo_url" in the database.
     */
    @SerializedName("html_url")
    @ColumnInfo(name = "repo_url")
    val repoURL: String,

    /**
     * Owner object containing login information (mapped from "owner").
     * - Stored as "owner_data" in the database.
     * - Note: Owner is a nested data class below.
     */
    @SerializedName("owner")
    @ColumnInfo(name = "owner_data")
    val owner: Owner,

    /**
     * Login username of the repository owner.
     * - Stored as "owner_login" in the database.
     */
    @ColumnInfo(name = "owner_login")
    val ownerLogin: String,

    /**
     * Description of the repository.
     * - This field is optional (nullable).
     */
    @SerializedName("description")
    val description: String?,

    /**
     * Primary programming language used in the repository.
     * - This field is optional (nullable).
     */
    @SerializedName("language")
    val language: String?,

    /**
     * Number of stars (stargazers) the repository has.
     * - Mapped from "stargazers_count" and stored as "stars".
     */
    @SerializedName("stargazers_count")
    @ColumnInfo(name = "stars")
    val stars: Int,

    /**
     * Number of forks the repository has.
     * - Mapped from "forks_count" and stored as "forks".
     */
    @SerializedName("forks_count")
    @ColumnInfo(name = "forks")
    val forks: Int
) {
    /**
     * Nested data class representing the repository owner.
     * - Contains only the login username from the API.
     */
    data class Owner(
        @SerializedName("login")
        val login: String
    )
}
