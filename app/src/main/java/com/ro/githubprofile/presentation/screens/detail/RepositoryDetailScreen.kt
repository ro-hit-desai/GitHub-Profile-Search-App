package com.ro.githubprofile.presentation.screens.detail

import android.os.Build
import android.view.MotionEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.ro.githubprofile.presentation.theme.GitHubProfileTheme

/**
 * Composable screen to display a GitHub repository webpage using WebView.
 *
 * @param url The URL of the repository to display.
 * @param darkTheme Whether the app is currently using dark theme.
 * @param onBack Callback invoked when back navigation is requested.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailScreen(url: String, darkTheme: Boolean, onBack: () -> Unit) {
    val webViewState = remember { WebViewState(url) }

    // Displays the WebView component with the given state and theme.
    WebView(
        state = webViewState,
        modifier = Modifier.fillMaxSize(),
        darkTheme = darkTheme
    )
}

/**
 * Composable that wraps a native Android WebView inside Jetpack Compose.
 *
 * - Configures common WebView settings (JavaScript, zoom, caching, dark mode).
 * - Updates the WebView's URL when state changes.
 * - Applies dark theme using `WebSettings.FORCE_DARK_ON` on Android 10+.
 * - Prevents parent views from intercepting scroll/touch events.
 *
 * @param state WebViewState containing the current URL.
 * @param modifier Modifier for layout adjustments.
 * @param darkTheme Boolean flag for enabling dark mode.
 */
@Composable
fun WebView(
    state: WebViewState,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = false
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    setSupportZoom(false)
                    builtInZoomControls = false
                    displayZoomControls = false
                    setLoadsImagesAutomatically(true)
                    cacheMode = WebSettings.LOAD_DEFAULT

                    // Enable WebView dark mode if supported and requested
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        forceDark = if (darkTheme) WebSettings.FORCE_DARK_ON else WebSettings.FORCE_DARK_OFF
                    }
                }

                // Configure scroll behavior and transparent background
                isVerticalScrollBarEnabled = true
                isHorizontalScrollBarEnabled = true
                scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
                overScrollMode = WebView.OVER_SCROLL_IF_CONTENT_SCROLLS
                setBackgroundColor(0x00000000)

                // Prevent parent components from intercepting scroll gestures
                setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN,
                        MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(true)
                    }
                    false
                }

                loadUrl(state.url.value)
            }
        },
        update = { webView ->
            // Update dark mode setting dynamically if supported
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                webView.settings.forceDark = if (darkTheme) WebSettings.FORCE_DARK_ON else WebSettings.FORCE_DARK_OFF
            }

            // Load the new URL only if it has changed
            if (webView.url != state.url.value) {
                webView.loadUrl(state.url.value)
            }
        },
        modifier = modifier
    )
}

/**
 * Holds the URL state for the WebView.
 *
 * This class can be extended later to hold more WebView-related states
 * like loading status, page title, etc.
 *
 * @param initialUrl The initial URL to load.
 */
class WebViewState(initialUrl: String) {
    val url: MutableState<String> = mutableStateOf(initialUrl)
}

/**
 * A preview of RepositoryDetailScreen in the light theme.
 */
@Preview(showBackground = true)
@Composable
fun RepositoryDetailScreenPreview() {
    GitHubProfileTheme {
        RepositoryDetailScreen(
            url = "https://api.github.com/users/vsouza",
            darkTheme = false,
            onBack = {}
        )
    }
}
