package com.example.aciflow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aciflow.nav.Screen
import com.example.aciflow.widgets.SimpleBottomAppBar
import com.example.aciflow.theme.ACIFlowTheme
import com.example.aciflow.views.homescreen.HomeScreen
import com.example.aciflow.views.login.LoginScreen

@Composable
fun AciFlowApp() {
    ACIFlowTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Navigation(
                    modifier = Modifier.padding(innerPadding),
                    appState
                )
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

@Composable
fun Navigation(modifier: Modifier, appState: AppState) {
    NavHost(navController = appState.navController,
            modifier = modifier,
        startDestination = Screen.LoginScreen.route) {

        composable(route = Screen.LoginScreen.route) {
            LoginScreen {
                route, popUp -> appState.navigateAndPopUp(route, popUp)
            }
        }

        composable(route = Screen.HomeScreen.route){
            HomeScreen {
                SimpleBottomAppBar(appState = appState)
            }
        }

        composable(route = Screen.RegisterScreen.route){
            //HomeScreen(navController = navController)
        }
    }
}