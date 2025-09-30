package com.ro.githubprofile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class for the GitHub Profile app.
 *
 * Annotated with @HiltAndroidApp to trigger Hilt's code generation.
 * This generates a base class for dependency injection that the app
 * can use to provide dependencies to all Android components.
 *
 * Hilt will automatically handle:
 * - Application-level singleton dependencies.
 * - Injection into Activities, Fragments, ViewModels, Services, etc.
 *
 * You must register this class in the AndroidManifest.xml as:
 * <application
 *     android:name=".GitHubProfileApp"
 *     ... />
 */
@HiltAndroidApp
class GitHubProfileApp : Application()
