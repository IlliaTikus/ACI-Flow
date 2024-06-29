package com.example.aciflow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AciFlowApp() {
    ACIFlowTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
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
                    modifier = Modifier.padding(innerPadding)
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
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    AppState(navController)
}
