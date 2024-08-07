package com.example.aciflow

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aciflow.common.snackbar.SnackbarManager
import com.example.aciflow.nav.Screen
import com.example.aciflow.theme.ACIFlowTheme
import com.example.aciflow.views.deadline.edit.EditDeadlineScreen
import com.example.aciflow.views.deadline.detail.DeadlineDetailScreen
import com.example.aciflow.views.deadlines.DeadlinesScreen
import com.example.aciflow.views.forum.HomeScreen
import com.example.aciflow.views.group.GroupScreen
import com.example.aciflow.views.login.LoginScreen
import com.example.aciflow.views.post.PostScreen
import com.example.aciflow.views.profile.ProfileScreen
import com.example.aciflow.views.register.RegisterScreen
import com.example.aciflow.widgets.SimpleBottomAppBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope

@Composable
fun AciFlowApp() {
    ACIFlowTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.primary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState,
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    if (currentRoute != Screen.LoginScreen.route && currentRoute != Screen.RegisterScreen.route) {
                        SimpleBottomAppBar(appState = appState)
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = appState.navController,
                    startDestination = Screen.LoginScreen.route,
                    modifier = Modifier.padding(innerPadding),
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable(Screen.LoginScreen.route) {
                        LoginScreen (navigate = {
                            route -> appState.navigate(route)
                        }) { route, popUp ->
                            appState.navigateAndPopUp(route, popUp)
                        }
                    }
                    composable(Screen.RegisterScreen.route) {
                        RegisterScreen { route, popUp ->
                            appState.navigateAndPopUp(route, popUp)
                        }
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen(appState.navController, appState)
                    }
                    composable(Screen.ForumScreen.route) {
                        HomeScreen {
                            appState.navigate(Screen.Post)
                        }
                    }
                    composable(Screen.Deadlines.route) {
                        DeadlinesScreen (appState.navController) { appState.navigate(Screen.EditDeadline) }
                    }
                    composable(
                        route = "${Screen.DeadlineDetail}/{deadlineId}",
                        arguments = listOf(navArgument("deadlineId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val deadlineId = backStackEntry.arguments?.getString("deadlineId") ?: ""
                        DeadlineDetailScreen(appState.navController, appState, deadlineId)
                    }
                    composable(Screen.EditDeadline.route) {
                        EditDeadlineScreen(appState.navController, appState)
                    }
                    composable(Screen.Group.route) {
                        GroupScreen()
                    }
                    composable(Screen.Post.route) {
                        PostScreen(appState.navController, appState)
                    }
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController, snackbarManager, coroutineScope) {
    AppState(scaffoldState, navController, snackbarManager, coroutineScope)
}
