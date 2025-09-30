package com.ro.githubprofile.presentation.screens.about

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ro.githubprofile.presentation.navigation.AppNavHost
import com.ro.githubprofile.presentation.theme.GitHubProfileTheme
import com.ro.githubprofile.presentation.viewmodel.GitHubViewModel
import kotlinx.coroutines.launch


/**
 *
 * AppContent(
 *     onThemeUpdated: (Boolean) -> Unit,
 *     darkTheme: Boolean,
 *     onBackPressed: () -> Unit,
 *     viewModel: GitHubViewModel = hiltViewModel()
 * )
 *
 * - Sets up navigation using NavController.
 * - Manages a modal navigation drawer for app sections (Home, About, Theme switch).
 * - Handles back button logic: closes drawer, exits search, pops navigation stack, or shows exit dialog.
 * - Displays an exit confirmation dialog when needed.
 * - Shows a TopAppBar with dynamic title and navigation icon based on current route.
 * - Hosts the main navigation graph via AppNavHost.
 * - Integrates theme switching via a Switch in the drawer.
 * - Uses Hilt for ViewModel injection.
 */



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    onThemeUpdated: (Boolean) -> Unit,
    darkTheme: Boolean,
    onBackPressed: () -> Unit,
    viewModel: GitHubViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showExitDialog by rememberSaveable { mutableStateOf(false) }
    val searchQuery by viewModel.searchQuery.collectAsState()
    var searchActive by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        when {
            drawerState.isOpen -> scope.launch { drawerState.close() }
            searchActive -> searchActive = false
            navController.currentBackStackEntry?.destination?.route != "repository_list" -> {
                navController.popBackStack()
            }
            else -> showExitDialog = true
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit App") },
            text = { Text("Are you sure you want to exit?") },
            confirmButton = {
                TextButton(onClick = { onBackPressed() }) { Text("Exit") }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) { Text("Cancel") }
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                drawerContentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(fraction = 0.7f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "GitHub Explorer",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        IconButton(onClick = { scope.launch { drawerState.close() } }) {
                            Icon(Icons.Default.Close, contentDescription = "Close drawer")
                        }
                    }

                    NavigationDrawerItem(
                        label = { Text("Home") },
                        selected = navController.currentBackStackEntryAsState().value?.destination?.hierarchy?.any {
                            it.route == "repository_list"
                        } == true,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("repository_list") {
                                popUpTo("repository_list") { inclusive = true }
                            }
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    )

                    NavigationDrawerItem(
                        label = { Text("About") },
                        selected = navController.currentBackStackEntryAsState().value?.destination?.hierarchy?.any {
                            it.route == "about"
                        } == true,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("about")
                        },
                        icon = { Icon(Icons.Default.Info, contentDescription = "About") },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Divider(modifier = Modifier.padding(vertical = 10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Dark Theme", style = MaterialTheme.typography.bodyLarge)
                        Switch(
                            checked = darkTheme,
                            onCheckedChange = { onThemeUpdated(it) }
                        )
                    }
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val isHome = currentRoute == "repository_list"

                if (isHome) {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Github Explorer",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        windowInsets = WindowInsets(0.dp)
                    )
                } else {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (currentRoute) {
                                    "about" -> "About"
                                    else -> "Repository Details"
                                },
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        },
                        windowInsets = WindowInsets(0.dp)
                    )
                }

                AppNavHost(
                    navController = navController,
                    darkTheme = darkTheme,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}






/**
 * AppContentPreview
 *
 * - Previews the AppContent composable in both light and dark modes using the Pixel 4 device.
 * - Wraps AppContent in the GitHubProfileTheme for consistent theming.
 * - Uses empty lambdas for callbacks and injects a ViewModel via Hilt.
 */
@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppContentPreview() {
    GitHubProfileTheme {
        AppContent(
            onThemeUpdated = {},
            darkTheme = false,
            onBackPressed = {},
            viewModel = hiltViewModel()
        )
    }
}