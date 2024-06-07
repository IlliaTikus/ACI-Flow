package com.example.aciflow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
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
import com.example.aciflow.views.deadlines.DeadlinesScreen
import com.example.aciflow.views.forum.ForumScreen
import com.example.aciflow.views.group.GroupScreen
import com.example.aciflow.views.homescreen.HomeScreen
import com.example.aciflow.views.login.LoginScreen
import com.example.aciflow.views.profile.ProfileScreen
import com.example.aciflow.views.deadline_detail.DeadlineDetailScreen
import com.example.aciflow.views.post.PostScreen
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
                topBar = {
                    if (currentRoute != Screen.LoginScreen.route) {
                        TopAppBar(title = { Text("ACI Flow") })
                    }
                },
                bottomBar = {
                    if (currentRoute != Screen.LoginScreen.route) {
                        SimpleBottomAppBar(appState = appState)
                    }
                },
                floatingActionButton = {
                    if (currentRoute == Screen.Deadlines.route) {
                        FloatingActionButton(onClick = { appState.navController.navigate(Screen.EditDeadline.route) }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Deadline")
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = appState.navController,
                    startDestination = Screen.LoginScreen.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.LoginScreen.route) {
                        LoginScreen { route, popUp ->
                            appState.navigateAndPopUp(route, popUp)
                        }
                    }
                    composable(Screen.HomeScreen.route) {
                        HomeScreen(appState.navController)
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen(appState.navController, appState)
                    }
                    composable(Screen.Forum.route) {
                        ForumScreen(appState.navController)
                    }
                    composable(Screen.Deadlines.route) {
                        DeadlinesScreen(appState.navController)
                    }
                    composable(Screen.DeadlineDetail.route, arguments = listOf(navArgument("deadlineTitle") { type = NavType.StringType })) { backStackEntry ->
                        val deadlineTitle = backStackEntry.arguments?.getString("deadlineTitle") ?: ""
                        DeadlineDetailScreen(deadlineTitle, appState.navController)
                    }
                    composable(Screen.EditDeadline.route) {
                        EditDeadlineScreen(appState.navController)
                    }
                    composable(Screen.Group.route) {
                        GroupScreen(appState.navController)
                    }
                    composable(Screen.Post.route) {
                        PostScreen(appState.navController)
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
